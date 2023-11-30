package jp.falsystack.cafekiosk.spring.api.controller.product;

import java.util.List;
import jp.falsystack.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import jp.falsystack.cafekiosk.spring.api.service.product.ProductService;
import jp.falsystack.cafekiosk.spring.api.service.product.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping("/api/v1/products/new")
  public ProductResponse createProduct(ProductCreateRequest request) {
    return productService.createProduct(request);
  }

  @GetMapping("/api/v1/products/selling")
  public List<ProductResponse> getSellingProducts() {
    return productService.getSellingProducts();
  }
}
