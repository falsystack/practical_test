package jp.falsystack.cafekiosk.unit;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import jp.falsystack.cafekiosk.unit.beverages.Beverage;
import jp.falsystack.cafekiosk.unit.order.Order;
import lombok.Getter;

@Getter
public class CafeKiosk {

  private static final LocalTime SHOP_OPEN_TIME = LocalTime.of(10, 0);
  private static final LocalTime SHOP_CLOSE_TIME = LocalTime.of(22, 0);

  private final List<Beverage> beverages = new ArrayList<>();

  public void add(Beverage beverage) {
    beverages.add(beverage);
  }

  public void add(Beverage beverage, int count) {
    if (count <= 0) {
      throw new IllegalArgumentException("飲み物は１杯以上からご注文お願いします。");
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
    var currentDateTime = LocalDateTime.now();
    var currentTime = currentDateTime.toLocalTime();
    if (currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)) {
      throw new IllegalArgumentException("注文時間ではございません、管理者にお問い合わせください。");
    }

    return new Order(currentDateTime, beverages);
  }

  // 特定の条件を外部から注入することによってテストがしやすくなる。
  public Order createOrder(LocalDateTime currentDateTime) {
    var currentTime = currentDateTime.toLocalTime();
    if (currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)) {
      throw new IllegalArgumentException("注文時間ではございません、管理者にお問い合わせください。");
    }

    return new Order(currentDateTime, beverages);
  }
}
