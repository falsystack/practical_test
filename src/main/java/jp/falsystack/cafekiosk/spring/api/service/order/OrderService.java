package jp.falsystack.cafekiosk.spring.api.service.order;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import jp.falsystack.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import jp.falsystack.cafekiosk.spring.api.service.order.request.OrderCreateServiceRequest;
import jp.falsystack.cafekiosk.spring.api.service.order.response.OrderResponse;
import jp.falsystack.cafekiosk.spring.domain.order.Order;
import jp.falsystack.cafekiosk.spring.domain.order.OrderRepository;
import jp.falsystack.cafekiosk.spring.domain.product.Product;
import jp.falsystack.cafekiosk.spring.domain.product.ProductRepository;
import jp.falsystack.cafekiosk.spring.domain.product.ProductType;
import jp.falsystack.cafekiosk.spring.domain.stock.Stock;
import jp.falsystack.cafekiosk.spring.domain.stock.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;
  private final StockRepository stockRepository;

  private static Map<String, Long> createCountingMapBy(List<String> stockProductNumbers) {
    return stockProductNumbers.stream()
        .collect(Collectors.groupingBy(pN -> pN, Collectors.counting()));
  }

  private static List<String> extractStockProductNumbers(List<Product> products) {
    return products.stream().filter(p -> ProductType.containsStockType(p.getType()))
        .map(Product::getProductNumber)
        .toList();
  }

  /**
   * 在庫を減らす　→　同時生（Concurrency）問題の悩みが必要
   * optimistic lock / pessimistic lock / ...
   */
  @Transactional
  public OrderResponse createOrder(OrderCreateServiceRequest request, LocalDateTime registeredDateTime) {
    var productNumbers = request.getProductNumbers();
    var products = findProductsBy(productNumbers);

    deductStockQuantities(products);

    var order = Order.create(products, registeredDateTime);
    var savedOrder = orderRepository.save(order);
    return OrderResponse.of(savedOrder);
  }

  private void deductStockQuantities(List<Product> products) {

    var stockProductNumbers = extractStockProductNumbers(products);

    var stockMap = createStockMapBy(stockProductNumbers);

    var productCountingMap = createCountingMapBy(stockProductNumbers);

    // 재고 차감 시도
    for (String stockProductNumber : new HashSet<>(stockProductNumbers)) {
      var stock = stockMap.get(stockProductNumber);
      var quantity = productCountingMap.get(stockProductNumber).intValue();
      if (stock.isQuantityLessThan(quantity)) {
        throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
      }
      stock.deductQuantity(quantity);
    }
  }

  private Map<String, Stock> createStockMapBy(List<String> stockProductNumbers) {
    // 재고 엔티티 조회
    var stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);
    // 이중 for문 방지를 위해 map을 만듦
    return stocks.stream().collect(Collectors.toMap(Stock::getProductNumber, s -> s));
  }

  private List<Product> findProductsBy(List<String> productNumbers) {
    var products = productRepository.findAllByProductNumberIn(productNumbers);

    var productMap = products.stream()
        .collect(Collectors.toMap(Product::getProductNumber, p -> p));

    return productNumbers.stream()
        .map(productMap::get).toList();
  }
}
