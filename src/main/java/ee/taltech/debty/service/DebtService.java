package ee.taltech.debty.service;

import ee.taltech.debty.entity.Debt;
import ee.taltech.debty.entity.Person;
import ee.taltech.debty.repository.DebtRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DebtService {

    private final DebtRepository debtRepository;
    private final UserService userService;

    public DebtService(DebtRepository debtRepository, UserService userService) {
        this.debtRepository = debtRepository;
        this.userService = userService;
    }

    public Debt saveDebt(Debt debt) {
        if (debt.getPayer().getId() == null) userService.saveUser(debt.getPayer());
        debt.setModifiedAt(LocalDateTime.now());
        return debtRepository.save(debt);
    }

    void saveDebts(List<Debt> debts) {
        debts.forEach(d -> d.setModifiedAt(LocalDateTime.now()));
        debtRepository.saveAll(debts);
    }

    public List<Debt> getAllDebts() {
        return debtRepository.findAll();
    }

    public Optional<Debt> getDebtById(Long id) {
        return debtRepository.findById(id);
    }

    public List<Debt> getAllDebtsByUserId(Long userId) {
        Optional<Person> userOptional = userService.getUserById(userId);
        if (userOptional.isPresent()) return debtRepository.findAllByPersonParticipating(userOptional.get());
        else return new ArrayList<>();
    }

    public void deleteDebt(Long id) {
        Optional<Debt> debtOptional = getDebtById(id);
        debtOptional.ifPresent(debtRepository::delete);
    }
}
