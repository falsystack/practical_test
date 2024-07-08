package jp.falsystack.cafekiosk.unit;

import jp.falsystack.cafekiosk.unit.beverages.Americano;
import jp.falsystack.cafekiosk.unit.beverages.Latte;

public class CafekioskRunner {
  public static void main(String[] args) {
    Cafekiosk cafekiosk = new Cafekiosk();
    cafekiosk.add(new Americano());
    System.out.println(">>> 아메리카노 추가");

    cafekiosk.add(new Latte());
    System.out.println(">>> 라뗴 추가");

    var totalPrice = cafekiosk.calculateTotalPrice();
    System.out.println("총 주문가격 = " + totalPrice);
  }
}
