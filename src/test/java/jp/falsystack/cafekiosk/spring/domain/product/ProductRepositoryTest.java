package jp.falsystack.cafekiosk.spring.domain.product;

import static jp.falsystack.cafekiosk.spring.domain.product.ProductSellingStatus.HOLD;
import static jp.falsystack.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static jp.falsystack.cafekiosk.spring.domain.product.ProductSellingStatus.STOP_SELLING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
class ProductRepositoryTest {

  @Autowired
  private ProductRepository productRepository;

  @Test
  @DisplayName("欲しい販売状態を持つプロダクトを照会する")
  void findAllBySellingStatusIn() {
    // given
    var product1 = Product.builder()
        .productNumber("001")
        .type(ProductType.HANDMADE)
        .sellingStatus(SELLING)
        .name("Americano")
        .price(400)
        .build();
    var product2 = Product.builder()
        .productNumber("002")
        .type(ProductType.HANDMADE)
        .sellingStatus(HOLD)
        .name("Latte")
        .price(450)
        .build();
    var product3 = Product.builder()
        .productNumber("003")
        .type(ProductType.HANDMADE)
        .sellingStatus(STOP_SELLING)
        .name("Mocha")
        .price(450)
        .build();
    productRepository.saveAll(List.of(product1, product2, product3));
    // when
    var products = productRepository.findAllBySellingStatusIn(
        List.of(SELLING, HOLD));

    // then
    assertThat(products).hasSize(2)
        .extracting("productNumber", "name", "sellingStatus")
        .containsExactlyInAnyOrder(
            tuple("001", "Americano", SELLING),
            tuple("002", "Latte", HOLD)
        );
  }

  @Test
  @DisplayName("商品番号リストで商品達を照会する")
  void findAllByProductNumberIn() {
    // given
    var product1 = Product.builder()
        .productNumber("001")
        .type(ProductType.HANDMADE)
        .sellingStatus(SELLING)
        .name("Americano")
        .price(400)
        .build();
    var product2 = Product.builder()
        .productNumber("002")
        .type(ProductType.HANDMADE)
        .sellingStatus(HOLD)
        .name("Latte")
        .price(450)
        .build();
    var product3 = Product.builder()
        .productNumber("003")
        .type(ProductType.HANDMADE)
        .sellingStatus(STOP_SELLING)
        .name("Mocha")
        .price(450)
        .build();
    productRepository.saveAll(List.of(product1, product2, product3));
    // when
    var products = productRepository.findAllByProductNumberIn(
        List.of("001", "002"));

    // then
    assertThat(products).hasSize(2)
        .extracting("productNumber", "name", "sellingStatus")
        .containsExactlyInAnyOrder(
            tuple("001", "Americano", SELLING),
            tuple("002", "Latte", HOLD)
        );
  }

}