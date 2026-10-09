package ecommerce.backend.repository.product;

import ecommerce.backend.model.product.ProductOptionValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductOptionValueRepository extends JpaRepository<ProductOptionValue, Long> {
    List<ProductOptionValue> findByOption_Product_Id(Long productId);

    Optional<ProductOptionValue> findByOptionIdAndValue(
            Long id,
            String value);

    void deleteByOptionId(Long id);
}
