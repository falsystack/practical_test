package jp.falsystack.cafekiosk.unit.order;

import java.time.LocalDateTime;
import java.util.List;
import jp.falsystack.cafekiosk.unit.beverages.Beverage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Order {
  private final LocalDateTime orderDateTime;

  private final List<Beverage> beverages;
}
