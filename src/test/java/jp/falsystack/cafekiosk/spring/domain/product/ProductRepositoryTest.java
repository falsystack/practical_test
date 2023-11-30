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

  private static Product createProduct(String productNumber, ProductType type,
      ProductSellingStatus sellingStatus, String name, int price) {
    return Product.builder()
        .productNumber(productNumber)
        .type(type)
        .sellingStatus(sellingStatus)
        .name(name)
        .price(price)
        .build();
  }

  @Test
  @DisplayName("欲しい販売状態を持つプロダクトを照会する")
  void findAllBySellingStatusIn() {
    // given
    var product1 = createProduct("001", ProductType.HANDMADE, SELLING, "Americano", 400);
    var product2 = createProduct("002", ProductType.HANDMADE, HOLD, "Latte", 450);
    var product3 = createProduct("003", ProductType.HANDMADE, STOP_SELLING, "Mocha", 450);
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
    var product1 = createProduct("001", ProductType.HANDMADE, SELLING, "Americano", 400);
    var product2 = createProduct("002", ProductType.HANDMADE, HOLD, "Latte", 450);
    var product3 = createProduct("003", ProductType.HANDMADE, STOP_SELLING, "Mocha", 450);
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

  @Test
  @DisplayName("もっとも最後に保存された商品の商品番号を読み取る")
  void findLatestProductNumber() {
    // given
    var targetProductNumber = "003";
    var product1 = createProduct("001", ProductType.HANDMADE, SELLING, "Americano", 400);
    var product2 = createProduct("002", ProductType.HANDMADE, HOLD, "Latte", 450);
    var product3 = createProduct(targetProductNumber, ProductType.HANDMADE, STOP_SELLING, "Mocha",
        450);
    productRepository.saveAll(List.of(product1, product2, product3));

    // when
    var latestProductNumber = productRepository.findLatestProductNumber();

    // then
    assertThat(latestProductNumber).isEqualTo(targetProductNumber);
  }

  @Test
  @DisplayName("もっとも最後に保存された商品の商品番号を読み取る時商品が一つもない場合は「Null」を返す")
  void findLatestProductNumberWhenProductIsEmpty() {
    // given

    // when
    var latestProductNumber = productRepository.findLatestProductNumber();

    // then
    assertThat(latestProductNumber).isNull();
  }

}