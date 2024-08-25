package jp.falsystack.cafekiosk.spring.api.service.order;

import jp.falsystack.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import jp.falsystack.cafekiosk.spring.api.service.order.response.OrderResponse;
import jp.falsystack.cafekiosk.spring.domain.order.Order;
import jp.falsystack.cafekiosk.spring.domain.order.OrderRepository;
import jp.falsystack.cafekiosk.spring.domain.product.Product;
import jp.falsystack.cafekiosk.spring.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        var productNumbers = request.getProductNumbers();

        var products = findProductsBy(productNumbers);

        var order = Order.create(products, registeredDateTime);
        var savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }

    private List<Product> findProductsBy(List<String> productNumbers) {
        var products = productRepository.findAllByProductNumberIn(productNumbers);
        var productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductNumber, p -> p));
        var duplicateProducts = productNumbers.stream()
                .map(num -> productMap.get(num))
                .toList();
        return duplicateProducts;
    }
}
