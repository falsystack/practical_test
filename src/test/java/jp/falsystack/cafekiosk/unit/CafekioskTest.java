package jp.falsystack.cafekiosk.unit;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import jp.falsystack.cafekiosk.unit.beverages.Americano;
import jp.falsystack.cafekiosk.unit.beverages.Latte;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CafekioskTest {

  @Test
  void add_manual_test() {
    Cafekiosk cafekiosk = new Cafekiosk();
    cafekiosk.add(new Americano());

    System.out.println(">>> 담긴 음료수 : " + cafekiosk.getBeverages());
    System.out.println(">>> 담긴 음료 : " + cafekiosk.getBeverages().get(0).getName());
  }

//  @DisplayName("음료 1개 추가 테스트")
  @DisplayName("음료 1개 추가하면 주문 목록에 담긴다.")
  @Test
  void add() {
    Cafekiosk cafekiosk = new Cafekiosk();
    cafekiosk.add(new Americano());

    assertThat(cafekiosk.getBeverages()).hasSize(1);
    assertThat(cafekiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
  }

  @Test
  void addSeveralBeverages() {
    Cafekiosk cafekiosk = new Cafekiosk();
    var americano = new Americano();
    cafekiosk.add(americano, 2);

    assertThat(cafekiosk.getBeverages().get(0)).isEqualTo(americano);
    assertThat(cafekiosk.getBeverages().get(1)).isEqualTo(americano);
  }

  @Test
  void addZeroBeverages() {
    Cafekiosk cafekiosk = new Cafekiosk();
    var americano = new Americano();

    assertThatThrownBy(() -> cafekiosk.add(americano, 0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("음료는 한 잔 이상 주문하실 수 있습니다.");
  }

  @Test
  void remove() {
    var cafekiosk = new Cafekiosk();
    var americano = new Americano();

    cafekiosk.add(americano);
    assertThat(cafekiosk.getBeverages()).hasSize(1);

    cafekiosk.remove(americano);
    assertThat(cafekiosk.getBeverages()).isEmpty();
  }

  @Test
  void clear() {
    var cafekiosk = new Cafekiosk();
    var americano = new Americano();
    var latte = new Latte();

    cafekiosk.add(americano);
    cafekiosk.add(latte);
    assertThat(cafekiosk.getBeverages()).hasSize(2);

    cafekiosk.clear();
    assertThat(cafekiosk.getBeverages()).isEmpty();
  }

  @DisplayName("주문 목록에 담긴 상품들의 총 금액을 계산할 수 있다.")
  @Test
  void calculateTotalPrice() {
    // given
    var cafekiosk = new Cafekiosk();
    var americano = new Americano();
    var latte = new Latte();

    cafekiosk.add(americano);
    cafekiosk.add(latte);

    // when
    var totalPrice = cafekiosk.calculateTotalPrice();

    // then
    assertThat(totalPrice).isEqualTo(8500);
  }

  //  @Test
  void createOrder() {
    var cafekiosk = new Cafekiosk();
    var americano = new Americano();

    cafekiosk.add(americano);

    var order = cafekiosk.createOrder();

    assertThat(order.getBeverages()).hasSize(1);
    assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
  }

  @Test
  void createOrderCurrentTime() {
    var cafekiosk = new Cafekiosk();
    var americano = new Americano();

    cafekiosk.add(americano);

    var order = cafekiosk.createOrder(LocalDateTime.of(2024, 7, 10, 22, 0));

    assertThat(order.getBeverages()).hasSize(1);
    assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
  }

  @Test
  void createOrderOutsideOpenTime() {
    var cafekiosk = new Cafekiosk();
    var americano = new Americano();

    cafekiosk.add(americano);

    assertThatThrownBy(() -> cafekiosk.createOrder(LocalDateTime.of(2024, 7, 10, 9, 59)))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
