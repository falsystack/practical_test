package jp.falsystack.cafekiosk.spring.domain.order;

import jp.falsystack.cafekiosk.spring.domain.product.Product;
import jp.falsystack.cafekiosk.spring.domain.product.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static jp.falsystack.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {

    @DisplayName("주문 생성 시 상품 리스트에서 주문의 총 금액을 계산한다.")
    @Test
    void calculateTotalPrice() {
        // given
        var products = List.of(
                createProduct("001", 1000),
                createProduct("002", 2000)
        );

        // when
        var order = Order.create(products, LocalDateTime.now());

        // then
        assertThat(order.getTotalPrice()).isEqualTo(3000);
    }

    @DisplayName("주문 생성 시 주문 상태는 INIT이다.")
    @Test
    void init() {
        // given
        var products = List.of(
                createProduct("001", 1000),
                createProduct("002", 2000)
        );

        // when
        var order = Order.create(products, LocalDateTime.now());

        // then
        assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);
    }

    @DisplayName("주문 생성 시 등록 시간을 기록한다.")
    @Test
    void registeredDateTime() {
        // given
        var registeredDateTime = LocalDateTime.now();
        var products = List.of(
                createProduct("001", 1000),
                createProduct("002", 2000)
        );

        // when
        var order = Order.create(products, registeredDateTime);

        // then
        assertThat(order.getRegisteredDatetime()).isEqualTo(registeredDateTime);
    }

    private Product createProduct(String productNumber, int price) {
        return Product.builder()
                .type(ProductType.HANDMADE)
                .productNumber(productNumber)
                .price(price)
                .sellingStatus(SELLING)
                .name("메뉴 이름")
                .build();
    }
}