package jp.falsystack.cafekiosk.spring.api.service.order;

import java.time.LocalDateTime;
import jp.falsystack.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import jp.falsystack.cafekiosk.spring.api.service.order.response.OrderResponse;
import jp.falsystack.cafekiosk.spring.domain.order.Order;
import jp.falsystack.cafekiosk.spring.domain.order.OrderRepository;
import jp.falsystack.cafekiosk.spring.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;

  public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
    var productNumbers = request.getProductNumbers();

    // product
    var products = productRepository.findAllByProductNumberIn(productNumbers);
    var order = Order.create(products, registeredDateTime);
    var savedOrder = orderRepository.save(order);
    return OrderResponse.of(savedOrder);
  }
}
