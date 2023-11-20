package jp.falsystack.cafekiosk.spring.domain.order;

import static jp.falsystack.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static jp.falsystack.cafekiosk.spring.domain.product.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import jp.falsystack.cafekiosk.spring.domain.product.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTest {

  @Test
  @DisplayName("注文生成時商品リストで注文の合計を計算する")
  void caculateTotalPrice() {
    // given
    var products = List.of(
        createProduct("001", 100),
        createProduct("002", 200));
    // when
    var order = Order.create(products, LocalDateTime.now());
    // then
    assertThat(order.getTotalPrice()).isEqualTo(300);
  }

  @Test
  @DisplayName("注文生成時注文状態はINITだ")
  void initStatus() {
    // given
    var products = List.of(
        createProduct("001", 100),
        createProduct("002", 200));
    // when
    var order = Order.create(products, LocalDateTime.now());
    // then
    assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);
  }

  @Test
  @DisplayName("注文生成時注文時間を記録する")
  void registeredDateTime() {
    // given
    var registeredDateTime = LocalDateTime.now();
    var products = List.of(
        createProduct("001", 100),
        createProduct("002", 200));
    // when
    var order = Order.create(products, registeredDateTime);
    // then
    assertThat(order.getRegisteredDateTime()).isEqualTo(registeredDateTime);
  }

  private Product createProduct(String productNumber, int price) {
    return Product.builder()
        .type(HANDMADE)
        .productNumber(productNumber)
        .price(price)
        .sellingStatus(SELLING)
        .name("メニュー名")
        .build();
  }

}