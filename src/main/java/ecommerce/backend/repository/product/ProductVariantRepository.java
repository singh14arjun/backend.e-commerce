package ecommerce.backend.repository.product;

import ecommerce.backend.constant.ProductVariantStatus;
import ecommerce.backend.model.product.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductVariantRepository extends JpaRepository<ProductVariant,Long> {

    List<ProductVariant> findByProductId(Long id);

    Optional<ProductVariant>  findBySku(String sku);

    boolean existsBySku(String sku);

    boolean existsBySkuAndIdNot(String sku,Long id);

    List<ProductVariant> findByProductIdAndStatus(
            Long id,
            ProductVariantStatus status);

}
