package jp.falsystack.cafekiosk.unit;

import jp.falsystack.cafekiosk.unit.beverages.Americano;
import jp.falsystack.cafekiosk.unit.beverages.Latte;

public class CafeKioskRunner {

  public static void main(String[] args) {
    // mainで手動的にやるのはよくない。
    var cafeKiosk = new CafeKiosk();
    cafeKiosk.add(new Americano());
    System.out.println(">>> アメリカの追加");
    cafeKiosk.add(new Latte());
    System.out.println(">>> ラテの追加");

    var totalPrice = cafeKiosk.calculateTotalPrice();
    System.out.println("合計：" + totalPrice);
  }
}
