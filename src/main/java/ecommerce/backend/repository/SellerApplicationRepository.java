package ecommerce.backend.repository;

import ecommerce.backend.model.seller.SellerApplication;
import ecommerce.backend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerApplicationRepository extends JpaRepository<SellerApplication, Long> {

    Optional<SellerApplication> findByUser(User user);

    boolean existsByUser(User user);

    boolean existsByStoreEmail(String storeEmail);


}
