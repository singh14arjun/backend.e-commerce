package ecommerce.backend.repository.category;

import ecommerce.backend.model.category.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    boolean existsByCategoryIdAndNameIgnoreCase(
            Long categoryId,
            String name
    );
    List<SubCategory> findByCategoryIdAndStatusTrue(
            Long categoryId
    );

    List<SubCategory> findByStatusTrue();

    Optional<SubCategory> findByIdAndStatusTrue( Long id
    );

}
