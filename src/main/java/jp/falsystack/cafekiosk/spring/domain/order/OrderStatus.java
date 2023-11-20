package jp.falsystack.cafekiosk.spring.domain.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

  INIT("注文生成"),
  CANCELED("注文取消"),
  PAYMENT_COMPLETED("決済完了"),
  PAYMENT_FAILED("決済失敗"),
  RECEIVED("注文受付"),
  COMPLETED("処理完了");

  private final String text;
}
