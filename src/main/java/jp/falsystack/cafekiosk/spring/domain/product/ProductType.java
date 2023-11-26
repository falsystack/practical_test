package jp.falsystack.cafekiosk.spring.domain.product;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductType {

  HANDMADE("手作り飲み物"),
  BOTTLE("ビン"),
  BAKERY("ベーカリー");
  private final String text;

  public static boolean containsStockType(ProductType type) {
    return List.of(BOTTLE, BAKERY).contains(type);
  }
}
