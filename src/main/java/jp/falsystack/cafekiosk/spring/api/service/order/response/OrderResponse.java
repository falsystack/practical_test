package jp.falsystack.cafekiosk.spring.api.service.order.response;

import java.time.LocalDateTime;
import java.util.List;
import jp.falsystack.cafekiosk.spring.api.service.product.response.ProductResponse;
import jp.falsystack.cafekiosk.spring.domain.order.Order;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderResponse {

  private final Long id;
  private final int totalPrice;
  private final LocalDateTime registeredDateTime;
  private final List<ProductResponse> products;

  @Builder
  public OrderResponse(Long id, int totalPrice, LocalDateTime registeredDateTime,
      List<ProductResponse> products) {
    this.id = id;
    this.totalPrice = totalPrice;
    this.registeredDateTime = registeredDateTime;
    this.products = products;
  }

  public static OrderResponse of(Order order) {
    return OrderResponse.builder()
        .id(order.getId())
        .totalPrice(order.getTotalPrice())
        .registeredDateTime(order.getRegisteredDateTime())
        .products(order.getOrderProducts().stream().map(op -> ProductResponse.of(op.getProduct()))
            .toList())
        .build();
  }
}
