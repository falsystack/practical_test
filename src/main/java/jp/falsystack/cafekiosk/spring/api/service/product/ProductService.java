package jp.falsystack.cafekiosk.spring.api.service.product;

import jp.falsystack.cafekiosk.spring.api.service.product.response.ProductResponse;
import jp.falsystack.cafekiosk.spring.domain.product.ProductRepository;
import jp.falsystack.cafekiosk.spring.domain.product.ProductSellingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProducts() {
        var products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
                .map(ProductResponse::of)
                .toList();
    }
}
