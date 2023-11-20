package jp.falsystack.cafekiosk.spring.api.service.order;

import static jp.falsystack.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.time.LocalDateTime;
import java.util.List;
import jp.falsystack.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import jp.falsystack.cafekiosk.spring.domain.product.Product;
import jp.falsystack.cafekiosk.spring.domain.product.ProductRepository;
import jp.falsystack.cafekiosk.spring.domain.product.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

//@DataJpaTest
@ActiveProfiles("test")
@SpringBootTest
class OrderServiceTest {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private OrderService orderService;

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
    var orderResponse = orderService.createOrder(request, registeredDateTime);

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