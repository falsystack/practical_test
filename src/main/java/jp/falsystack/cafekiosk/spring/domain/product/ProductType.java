package jp.falsystack.cafekiosk.spring.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductType {

  HANDMADE("手作り飲み物"),
  BOTTLE("ビン"),
  BAKERY("ベーカリー");
  private final String text;
}
