package ee.taltech.debty.repository;

import ee.taltech.debty.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Person, Long> {

    Person findByEmail(String email);

}
