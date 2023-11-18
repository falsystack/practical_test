package jp.falsystack.cafekiosk.unit;

import jp.falsystack.cafekiosk.unit.beverages.Americano;
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

}