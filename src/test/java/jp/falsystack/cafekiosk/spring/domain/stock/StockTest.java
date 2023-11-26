package jp.falsystack.cafekiosk.spring.domain.stock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StockTest {

  @Test
  @DisplayName("在庫の数量が提供された数量より少ないか確認する")
  void isQuantityLessThan() {
    // given
    var stock = Stock.create("001", 1);
    int quantity = 2;

    // when
    var result = stock.isQuantityLessThan(quantity);

    // then
    assertThat(result).isTrue();
  }

  @Test
  @DisplayName("在庫を与えられた数量分減らせる")
  void deductQuantity() {
    // given
    var stock = Stock.create("001", 1);
    var quantity = 1;

    // when
    stock.deductQuantity(quantity);

    // then
    assertThat(stock.getQuantity()).isZero();
  }

  @Test
  @DisplayName("在庫の数量以上減らしようとすると例外が発生する")
  void deductQuantityException() {
    // given
    var stock = Stock.create("001", 1);
    var quantity = 2;

    // expected
    assertThatThrownBy(() -> stock.deductQuantity(quantity)).isInstanceOf(
        IllegalArgumentException.class).hasMessage("차감할 재고 수량이 없습니다.");
  }

}