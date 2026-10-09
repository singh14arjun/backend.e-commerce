package ecommerce.backend.repository.product;

import ecommerce.backend.model.product.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOptionRepository
                     extends JpaRepository<ProductOption,Long> {

    List<ProductOption> findByProductId(Long id);

    boolean existsByProductIdAndName(
            Long id,
            String name);

    void deleteByProductId(Long id);
}
