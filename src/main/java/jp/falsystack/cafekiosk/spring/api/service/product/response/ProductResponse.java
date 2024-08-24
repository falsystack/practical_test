package jp.falsystack.cafekiosk.spring.api.service.product.response;

import jp.falsystack.cafekiosk.spring.domain.product.Product;
import jp.falsystack.cafekiosk.spring.domain.product.ProductSellingStatus;
import jp.falsystack.cafekiosk.spring.domain.product.ProductType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductResponse {
    private Long id;
    private String productNumber;
    private ProductType type;
    private ProductSellingStatus sellingType;
    private String name;
    private int price;


    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productNumber(product.getProductNumber())
                .type(product.getType())
                .sellingType(product.getSellingStatus())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
