package ee.taltech.debty.repository;

import ee.taltech.debty.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
