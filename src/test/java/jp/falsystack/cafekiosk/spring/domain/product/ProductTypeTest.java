package jp.falsystack.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTypeTest {

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockTypeHandmade() {
        // given
        var givenType = ProductType.HANDMADE;

        // when
        var result = ProductType.containsStockType(givenType);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockTypeBakery() {
        // given
        var givenType = ProductType.BAKERY;

        // when
        var result = ProductType.containsStockType(givenType);

        // then
        assertThat(result).isTrue();
    }
}