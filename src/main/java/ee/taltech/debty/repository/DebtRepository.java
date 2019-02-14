package ee.taltech.debty.repository;

import ee.taltech.debty.entity.Debt;
import ee.taltech.debty.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Long> {

    @Query("SELECT d FROM Debt d WHERE d.payer = ?1 OR d.receiver = ?1")
    List<Debt> findAllByPersonParticipating(Person person);

}
