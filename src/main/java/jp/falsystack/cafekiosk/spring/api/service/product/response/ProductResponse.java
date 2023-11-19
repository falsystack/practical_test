package jp.falsystack.cafekiosk.spring.api.service.product.response;

import jp.falsystack.cafekiosk.spring.domain.product.Product;
import jp.falsystack.cafekiosk.spring.domain.product.ProductSellingStatus;
import jp.falsystack.cafekiosk.spring.domain.product.ProductType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponse {

  private Long id;
  private String productNumber;
  private ProductType type;
  private ProductSellingStatus sellingStatus;
  private String name;
  private int price;

  @Builder
  private ProductResponse(Long id, String productNumber, ProductType type,
      ProductSellingStatus sellingStatus, String name, int price) {
    this.id = id;
    this.productNumber = productNumber;
    this.type = type;
    this.sellingStatus = sellingStatus;
    this.name = name;
    this.price = price;
  }

  public static ProductResponse of(Product p) {
    return ProductResponse.builder()
        .id(p.getId())
        .productNumber(p.getProductNumber())
        .type(p.getType())
        .sellingStatus(p.getSellingStatus())
        .name(p.getName())
        .price(p.getPrice())
        .build();
  }
}
