package ecommerce.backend.repository.category;

import ecommerce.backend.constant.CategoryStatus;
import ecommerce.backend.model.category.Category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByNameIgnoreCase( String name);

    List<Category> findAllByStatus(CategoryStatus status);
}