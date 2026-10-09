package ecommerce.backend.repository.product;

import ecommerce.backend.constant.ProductStatus;
import ecommerce.backend.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsBySlug(String slug);

    boolean existsBySlugAndIdNot(String slug, Long id);

    Page<Product> findByStatus(
            ProductStatus productStatus,
            Pageable pageable
    );

    Page<Product> findBySellerId(Long sellerId, Pageable pageable);

    Page<Product> findBySellerIdAndStatus(
            Long sellerId,
            ProductStatus productStatus,
            Pageable pageable
    );

    // Products by subcategory ID
    List<Product> findBySubCategory_Id(Long subCategoryId);

    // Products by category ID through SubCategory
    List<Product> findBySubCategory_Category_Id(Long categoryId);

    // Products by brand ID
    List<Product> findByBrand_Id(Long brandId);
}