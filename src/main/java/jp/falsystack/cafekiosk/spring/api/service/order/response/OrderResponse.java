package jp.falsystack.cafekiosk.spring.api.service.order.response;

import jp.falsystack.cafekiosk.spring.api.service.product.response.ProductResponse;
import jp.falsystack.cafekiosk.spring.domain.order.Order;
import jp.falsystack.cafekiosk.spring.domain.order.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderResponse {

    private Long id;
    private OrderStatus orderStatus;
    private int totalPrice;
    private LocalDateTime registeredDateTime;

    private List<ProductResponse> products;

    @Builder
    public OrderResponse(Long id, OrderStatus orderStatus, int totalPrice, LocalDateTime registeredDateTime, List<ProductResponse> products) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.registeredDateTime = registeredDateTime;
        this.products = products;
    }

    public static OrderResponse of(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .registeredDateTime(order.getRegisteredDatetime())
                .products(order.getOrderProducts().stream()
                        .map(orderProduct -> ProductResponse.of(orderProduct.getProduct()))
                        .toList())
                .build();
    }

}
