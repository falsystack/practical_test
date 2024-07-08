package jp.falsystack.cafekiosk.unit.order;

import java.time.LocalDateTime;
import java.util.List;
import jp.falsystack.cafekiosk.unit.beverages.Beverage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Order {
  private final LocalDateTime orderDateTime;

  private final List<Beverage> beverages;
}
