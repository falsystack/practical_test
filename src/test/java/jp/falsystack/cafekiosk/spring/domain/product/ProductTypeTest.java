package jp.falsystack.cafekiosk.spring.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTypeTest {

  @Test
  @DisplayName("商品タイプが在庫関連タイプであるとTrueを返す")
  void containsStockTypeTrue() {
    // given
    var type1 = ProductType.BOTTLE;
    var type2 = ProductType.BAKERY;
    // when
    var result1 = ProductType.containsStockType(type1);
    var result2 = ProductType.containsStockType(type2);

    // then
    assertThat(result1).isTrue();
    assertThat(result2).isTrue();
  }

  @Test
  @DisplayName("商品タイプが在庫関連タイプではないとFalseを返す")
  void containsStockTypeFalse() {
    // given
    var type = ProductType.HANDMADE;
    // when
    var result = ProductType.containsStockType(type);

    // then
    assertThat(result).isFalse();
  }

}