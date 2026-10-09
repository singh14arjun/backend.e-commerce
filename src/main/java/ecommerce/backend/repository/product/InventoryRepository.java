package ecommerce.backend.repository.product;

import ecommerce.backend.model.product.Inventory;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InventoryRepository extends CrudRepository<Inventory, Long> {

    Optional<Inventory> findByVariantId(Long variantId);

    boolean existsByVariantId(Long variantId);
}
