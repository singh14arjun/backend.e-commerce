package ecommerce.backend.repository.product;

import ecommerce.backend.model.product.ProductVariantOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductVariantOptionRepository extends JpaRepository<ProductVariantOption, Long> {

    List<ProductVariantOption> findByVariantId(Long variantId);

    void deleteByVariantId(Long variantId);
}
