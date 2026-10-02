package ecommerce.backend.repository;

import ecommerce.backend.model.seller.Seller;
import ecommerce.backend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    Optional<Seller> findByUser(User user);

    boolean existsByUser(User user);

    boolean existsByStoreEmail(String storeEmail);}
