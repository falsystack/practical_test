package jp.falsystack.cafekiosk.spring.api.controller.product.dto.request;

import jp.falsystack.cafekiosk.spring.domain.product.Product;
import jp.falsystack.cafekiosk.spring.domain.product.ProductSellingStatus;
import jp.falsystack.cafekiosk.spring.domain.product.ProductType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCreateRequest {

  private ProductType type;
  private ProductSellingStatus sellingStatus;
  private String name;
  private int price;

  @Builder
  public ProductCreateRequest(ProductType type, ProductSellingStatus sellingStatus, String name,
      int price) {
    this.type = type;
    this.sellingStatus = sellingStatus;
    this.name = name;
    this.price = price;
  }

  public Product toEntity(String productNumber) {
    return Product.builder()
        .productNumber(productNumber)
        .name(name)
        .sellingStatus(sellingStatus)
        .price(price)
        .type(type)
        .build();
  }
}
