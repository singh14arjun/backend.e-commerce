package ecommerce.backend.repository;

import ecommerce.backend.constant.BrandStatus;
import ecommerce.backend.model.brand.Brand;
import ecommerce.backend.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    List<Brand> findAllByStatus(BrandStatus status);

}
