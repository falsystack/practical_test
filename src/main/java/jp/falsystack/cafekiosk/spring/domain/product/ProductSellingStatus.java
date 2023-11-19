package jp.falsystack.cafekiosk.spring.domain.product;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductSellingStatus {

  SELLING("販売中"),
  HOLD("販売保留"),
  STOP_SELLING("販売停止");
  private final String text;

  public static List<ProductSellingStatus> forDisplay() {
    return List.of(SELLING, HOLD);
  }
}
