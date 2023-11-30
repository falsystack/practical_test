package jp.falsystack.cafekiosk.spring.api.service.product;

import java.util.List;
import jp.falsystack.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import jp.falsystack.cafekiosk.spring.api.service.product.response.ProductResponse;
import jp.falsystack.cafekiosk.spring.domain.product.ProductRepository;
import jp.falsystack.cafekiosk.spring.domain.product.ProductSellingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  @Transactional
  public ProductResponse createProduct(ProductCreateRequest request) {
    var nextProductNumber = createNextProductNumber();

    var product = request.toEntity(nextProductNumber);
    var savedProduct = productRepository.save(product);

    return ProductResponse.of(savedProduct);
  }

  public List<ProductResponse> getSellingProducts() {
    var products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());
    return products.stream().map(ProductResponse::of).toList();
  }

  // DB에서 마지막 저장된 프로덕트의 상품번호를 읽어와서 +1 해준다.
  private String createNextProductNumber() {
    var latestProductNumber = productRepository.findLatestProductNumber();
    if (latestProductNumber == null) {
      return "001";
    }

    var intLatestProductNumber = Integer.valueOf(latestProductNumber);
    var nextProductNumberInt = intLatestProductNumber + 1;

    // 9 -> 009, 10 -> 010
    return String.format("%03d", nextProductNumberInt);
  }
}
