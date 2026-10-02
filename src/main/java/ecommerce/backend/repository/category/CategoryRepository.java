package ecommerce.backend.repository.category;

import ecommerce.backend.model.category.Category;
import ecommerce.backend.model.seller.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByNameIgnoreCase( String name);
}