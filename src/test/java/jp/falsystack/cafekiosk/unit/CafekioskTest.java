package jp.falsystack.cafekiosk.unit;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import jp.falsystack.cafekiosk.unit.beverages.Americano;
import jp.falsystack.cafekiosk.unit.beverages.Latte;
import org.junit.jupiter.api.Test;

class CafekioskTest {

  @Test
  void add_manual_test() {
    Cafekiosk cafekiosk = new Cafekiosk();
    cafekiosk.add(new Americano());

    System.out.println(">>> 담긴 음료수 : " + cafekiosk.getBeverages());
    System.out.println(">>> 담긴 음료 : " + cafekiosk.getBeverages().get(0).getName());
  }

  @Test
  void add() {
    Cafekiosk cafekiosk = new Cafekiosk();
    cafekiosk.add(new Americano());

    assertThat(cafekiosk.getBeverages()).hasSize(1);
    assertThat(cafekiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
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
}
