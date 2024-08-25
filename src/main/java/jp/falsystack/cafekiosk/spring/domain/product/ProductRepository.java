package jp.falsystack.cafekiosk.spring.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * expected
     *
     * select *
     * from product p
     * where p.product_selling_status in (?...)
     */
    List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> sellingStatus);

    /**
     * expected
     * <p>
     * select *
     * from product p
     * where p.product_number in (?...)
     */
    List<Product> findAllByProductNumberIn(List<String> productNumbers);
}
