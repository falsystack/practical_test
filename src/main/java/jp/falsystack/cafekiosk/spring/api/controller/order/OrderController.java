package jp.falsystack.cafekiosk.spring.api.controller.order;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import jp.falsystack.cafekiosk.spring.api.ApiResponse;
import jp.falsystack.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import jp.falsystack.cafekiosk.spring.api.service.order.OrderService;
import jp.falsystack.cafekiosk.spring.api.service.order.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping("/api/v1/orders/new")
  public ApiResponse<OrderResponse> createOrder(@RequestBody @Valid OrderCreateRequest request) {
    var registeredDateTime = LocalDateTime.now();
    return ApiResponse.ok(orderService.createOrder(request.toServiceRequest(), registeredDateTime));
  }
}
