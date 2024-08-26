package jp.falsystack.cafekiosk.spring.api.service.order;

import jp.falsystack.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import jp.falsystack.cafekiosk.spring.api.service.order.response.OrderResponse;
import jp.falsystack.cafekiosk.spring.domain.order.Order;
import jp.falsystack.cafekiosk.spring.domain.order.OrderRepository;
import jp.falsystack.cafekiosk.spring.domain.product.Product;
import jp.falsystack.cafekiosk.spring.domain.product.ProductRepository;
import jp.falsystack.cafekiosk.spring.domain.product.ProductType;
import jp.falsystack.cafekiosk.spring.domain.stock.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        var productNumbers = request.getProductNumbers();
        var products = findProductsBy(productNumbers);

        // 재고 차감 체크가 필요한 상품들 filter
        products.stream()
                .filter(product -> ProductType.containsStockType(product.getType()))
                .map(Product::getProductNumber)
                .toList();

        // 재고 엔티티 조회
        // 상품별 counting
        // 재고 차감 시도

        var order = Order.create(products, registeredDateTime);
        var savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }

    private List<Product> findProductsBy(List<String> productNumbers) {
        var products = productRepository.findAllByProductNumberIn(productNumbers);
        var productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductNumber, p -> p));
        var duplicateProducts = productNumbers.stream()
                .map(num -> productMap.get(num))
                .toList();
        return duplicateProducts;
    }
}
