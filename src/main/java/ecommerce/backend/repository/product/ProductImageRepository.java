package ecommerce.backend.repository.product;

import ecommerce.backend.model.product.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductIdOrderByDisplayOrderAsc(
            Long productId
    );

    void deleteByProductId(Long productId);
}
