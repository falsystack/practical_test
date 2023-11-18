package jp.falsystack.cafekiosk.unit;

import static org.assertj.core.api.Assertions.assertThat;

import jp.falsystack.cafekiosk.unit.beverages.Americano;
import jp.falsystack.cafekiosk.unit.beverages.Latte;
import org.junit.jupiter.api.Test;

class CafeKioskTest {

  @Test
  void add() {
    var cafeKiosk = new CafeKiosk();
    cafeKiosk.add(new Americano());

    // 人の目で確認するテストはよくない。
    System.out.println(">>> 飲み物の合計：" + cafeKiosk.getBeverages().size());
    System.out.println(">>> 含められた飲み物：" + cafeKiosk.getBeverages().get(0).getName());
  }

  @Test
  void add_auto() {
    var cafeKiosk = new CafeKiosk();
    cafeKiosk.add(new Americano());

    assertThat(cafeKiosk.getBeverages()).hasSize(1);
    assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("Americano");
  }

  @Test
  void remove() {
    var cafeKiosk = new CafeKiosk();
    var americano = new Americano();

    cafeKiosk.add(americano);
    assertThat(cafeKiosk.getBeverages()).hasSize(1);

    cafeKiosk.remove(americano);
    assertThat(cafeKiosk.getBeverages()).hasSize(0);
  }

  @Test
  void clear() {
    var cafeKiosk = new CafeKiosk();
    var americano = new Americano();
    var latte = new Latte();

    cafeKiosk.add(americano);
    cafeKiosk.add(latte);
    assertThat(cafeKiosk.getBeverages()).hasSize(2);

    cafeKiosk.clear();
    assertThat(cafeKiosk.getBeverages()).hasSize(0);
  }
}