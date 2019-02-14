package ee.taltech.debty.repository;

import ee.taltech.debty.entity.Event;
import ee.taltech.debty.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByUsersContaining(User user);

}
