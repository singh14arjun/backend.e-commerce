package ecommerce.backend.repository;

import ecommerce.backend.constant.Role;
import ecommerce.backend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
    List<User> findAllByRole(Role role);
}
