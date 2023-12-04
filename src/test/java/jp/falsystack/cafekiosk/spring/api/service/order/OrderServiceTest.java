package jp.falsystack.cafekiosk.spring.api.service.order;

import static jp.falsystack.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

import java.time.LocalDateTime;
import java.util.List;
import jp.falsystack.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import jp.falsystack.cafekiosk.spring.domain.order.OrderRepository;
import jp.falsystack.cafekiosk.spring.domain.orderproduct.OrderProductRepository;
import jp.falsystack.cafekiosk.spring.domain.product.Product;
import jp.falsystack.cafekiosk.spring.domain.product.ProductRepository;
import jp.falsystack.cafekiosk.spring.domain.product.ProductType;
import jp.falsystack.cafekiosk.spring.domain.stock.Stock;
import jp.falsystack.cafekiosk.spring.domain.stock.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
class OrderServiceTest {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private OrderProductRepository orderProductRepository;

  @Autowired
  private StockRepository stockRepository;

  @Autowired
  private OrderService orderService;

  @AfterEach
  void tearDown() {
    orderProductRepository.deleteAllInBatch();
    productRepository.deleteAllInBatch();
    orderRepository.deleteAllInBatch();
    stockRepository.deleteAllInBatch();
  }

  @Test
  @DisplayName("注文番号リストを受け取って注文を生成する")
  void createOrder() {
    // given
    var product1 = createProduct(ProductType.HANDMADE, "001", 100);
    var product2 = createProduct(ProductType.HANDMADE, "002", 200);
    var product3 = createProduct(ProductType.HANDMADE, "003", 300);
    productRepository.saveAll(List.of(product1, product2, product3));

    var request = OrderCreateRequest.builder()
        .productNumbers(List.of("001", "002"))
        .build();
    var registeredDateTime = LocalDateTime.now();
    // when
    var orderResponse = orderService.createOrder(request.toServiceRequest(), registeredDateTime);

    // then
    assertThat(orderResponse.getId()).isNotNull();
    assertThat(orderResponse)
        .extracting("registeredDateTime", "totalPrice")
        .containsExactlyInAnyOrder(registeredDateTime, 300);

    assertThat(orderResponse.getProducts()).hasSize(2)
        .extracting("productNumber", "price")
        .containsExactlyInAnyOrder(
            tuple("001", 100),
            tuple("002", 200)
        );
  }

  @Test
  @DisplayName("在庫と関連つけられた商品が含まれている注文番号リストを受取注文を生成する")
  void createOrderWithStock() {
    // given
    var product1 = createProduct(ProductType.BOTTLE, "001", 100);
    var product2 = createProduct(ProductType.BAKERY, "002", 300);
    var product3 = createProduct(ProductType.HANDMADE, "003", 500);
    productRepository.saveAll(List.of(product1, product2, product3));

    var stock1 = Stock.create("001", 2);
    var stock2 = Stock.create("002", 2);
    stockRepository.saveAll(List.of(stock1, stock2));

    var request = OrderCreateRequest.builder()
        .productNumbers(List.of("001", "001", "002", "003"))
        .build();
    var registeredDateTime = LocalDateTime.now();
    // when
    var orderResponse = orderService.createOrder(request.toServiceRequest(), registeredDateTime);

    // then
    assertThat(orderResponse.getId()).isNotNull();
    assertThat(orderResponse)
        .extracting("registeredDateTime", "totalPrice")
        .containsExactlyInAnyOrder(registeredDateTime, 1000);

    assertThat(orderResponse.getProducts()).hasSize(4)
        .extracting("productNumber", "price")
        .containsExactlyInAnyOrder(
            tuple("001", 100),
            tuple("001", 100),
            tuple("002", 300),
            tuple("003", 500)
        );

    var stocks = stockRepository.findAll();
    assertThat(stocks).hasSize(2)
        .extracting("productNumber", "quantity")
        .containsExactlyInAnyOrder(
            tuple("001", 0),
            tuple("002", 1)
        );
  }

  @Test
  @DisplayName("在庫がない商品で注文を生成しようとする場合例外が発生する")
  void createOrderWithStockException() {
    // given
    var product1 = createProduct(ProductType.BOTTLE, "001", 100);
    var product2 = createProduct(ProductType.BAKERY, "002", 300);
    var product3 = createProduct(ProductType.HANDMADE, "003", 500);
    productRepository.saveAll(List.of(product1, product2, product3));

    var stock1 = Stock.create("001", 1);
    var stock2 = Stock.create("002", 1);
    stockRepository.saveAll(List.of(stock1, stock2));

    var request = OrderCreateRequest.builder()
        .productNumbers(List.of("001", "001", "002", "003"))
        .build();
    var registeredDateTime = LocalDateTime.now();

    // expected
    assertThatThrownBy(() -> orderService.createOrder(request.toServiceRequest(), registeredDateTime))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("재고가 부족한 상품이 있습니다.");
  }

  @Test
  @DisplayName("重複される商品番号リストで注文を生成することが出来る")
  void createOrderWithDuplicateProductNumber() {
    // given
    var product1 = createProduct(ProductType.HANDMADE, "001", 100);
    var product2 = createProduct(ProductType.HANDMADE, "002", 200);
    var product3 = createProduct(ProductType.HANDMADE, "003", 300);
    productRepository.saveAll(List.of(product1, product2, product3));

    var request = OrderCreateRequest.builder()
        .productNumbers(List.of("001", "001"))
        .build();
    var registeredDateTime = LocalDateTime.now();
    // when
    var orderResponse = orderService.createOrder(request.toServiceRequest(), registeredDateTime);

    // then
    assertThat(orderResponse.getId()).isNotNull();
    assertThat(orderResponse)
        .extracting("registeredDateTime", "totalPrice")
        .containsExactlyInAnyOrder(registeredDateTime, 200);

    assertThat(orderResponse.getProducts()).hasSize(2)
        .extracting("productNumber", "price")
        .containsExactlyInAnyOrder(
            tuple("001", 100),
            tuple("001", 100)
        );
  }

  private Product createProduct(ProductType type, String productNumber, int price) {
    return Product.builder()
        .type(type)
        .productNumber(productNumber)
        .price(price)
        .sellingStatus(SELLING)
        .name("メニュー名")
        .build();
  }
}