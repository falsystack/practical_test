package jp.falsystack.cafekiosk.unit;

import static org.junit.jupiter.api.Assertions.*;

import jp.falsystack.cafekiosk.unit.beverages.Americano;
import org.junit.jupiter.api.Test;

class CafekioskTest {

  @Test
  void add() {
    Cafekiosk cafekiosk = new Cafekiosk();
    cafekiosk.add(new Americano());

    System.out.println(">>> 담긴 음료수 : " + cafekiosk.getBeverages());
    System.out.println(">>> 담긴 음료 : " + cafekiosk.getBeverages().get(0).getName());
  }
}
