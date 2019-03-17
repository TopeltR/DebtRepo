package ee.taltech.debty.repository;

import ee.taltech.debty.entity.Contact;
import ee.taltech.debty.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository <Contact, Long> {

    List<Contact> findAllByTo(Person person);

    List<Contact> findAllByFrom(Person person);

    Optional<Contact> findByFrom(Person person);

    void removeContactByTo(Person person);

    void removeContactByFrom(Person person);
}
