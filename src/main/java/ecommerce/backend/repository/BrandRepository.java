package ecommerce.backend.repository;

import ecommerce.backend.model.brand.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
