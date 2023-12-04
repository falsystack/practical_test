package jp.falsystack.cafekiosk.spring.api.service.order.request;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCreateServiceRequest {

  private List<String> productNumbers;

  @Builder
  public OrderCreateServiceRequest(List<String> productNumbers) {
    this.productNumbers = productNumbers;
  }
}
