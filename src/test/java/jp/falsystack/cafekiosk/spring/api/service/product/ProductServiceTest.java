package jp.falsystack.cafekiosk.spring.api.service.product;

import static jp.falsystack.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static jp.falsystack.cafekiosk.spring.domain.product.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import jp.falsystack.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import jp.falsystack.cafekiosk.spring.domain.product.Product;
import jp.falsystack.cafekiosk.spring.domain.product.ProductRepository;
import jp.falsystack.cafekiosk.spring.domain.product.ProductSellingStatus;
import jp.falsystack.cafekiosk.spring.domain.product.ProductType;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class ProductServiceTest {

  @Autowired
  private ProductService productService;

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

  @AfterEach
  void tearDown() {
    productRepository.deleteAllInBatch();
  }

  @Test
  @DisplayName("新規商品を登録する。商品番号は一番最近の商品番号から１増加した値である。")
  void createProduct() {
    // given
    var product1 = createProduct("001", HANDMADE, SELLING, "Americano", 400);
    productRepository.save(product1);

    var request = ProductCreateRequest.builder()
        .type(HANDMADE)
        .sellingStatus(SELLING)
        .name("Caramel Machi")
        .price(500)
        .build();

    // when
    productService.createProduct(request);

    // then
    var products = productRepository.findAll();
    assertThat(products)
        .hasSize(2)
        .extracting("productNumber", "type", "sellingStatus", "name", "price")
        .containsExactlyInAnyOrder(
            Tuple.tuple("001", HANDMADE, SELLING, "Americano", 400),
            Tuple.tuple("002", HANDMADE, SELLING, "Caramel Machi", 500)
        );

  }

  @Test
  @DisplayName("商品が一つもない時、新規商品を登録すると商品番号は001である")
  void createProductWhenProductIsEmpty() {
    // given
    var request = ProductCreateRequest.builder()
        .type(HANDMADE)
        .sellingStatus(SELLING)
        .name("Caramel Machi")
        .price(500)
        .build();

    // when
    productService.createProduct(request);

    // then
    var products = productRepository.findAll();
    assertThat(products)
        .hasSize(1)
        .extracting("productNumber", "type", "sellingStatus", "name", "price")
        .contains(
            Tuple.tuple("001", HANDMADE, SELLING, "Caramel Machi", 500)
        );
  }

}