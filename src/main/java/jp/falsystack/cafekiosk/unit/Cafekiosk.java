package jp.falsystack.cafekiosk.unit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jp.falsystack.cafekiosk.unit.beverages.Beverage;
import jp.falsystack.cafekiosk.unit.order.Order;
import lombok.Getter;

@Getter
public class Cafekiosk {

  private final List<Beverage> beverages = new ArrayList<>();

  public void add(Beverage beverage) {
    beverages.add(beverage);
  }

  public void add(Beverage beverage, int count) {
    if (count <= 0) {
      throw new IllegalArgumentException("음료는 한 잔 이상 주문하실 수 있습니다.");
    }

    for (int i = 0; i < count; i++) {
      beverages.add(beverage);
    }
  }

  public void remove(Beverage beverage) {
    beverages.remove(beverage);
  }

  public void clear() {
    beverages.clear();
  }

  public int calculateTotalPrice() {
    return beverages.stream().mapToInt(Beverage::getPrice).sum();
  }

  public Order createOrder() {
    return new Order(LocalDateTime.now(), beverages);
  }
}
