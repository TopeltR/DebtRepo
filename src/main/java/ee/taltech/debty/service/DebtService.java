package ee.taltech.debty.service;

import ee.taltech.debty.entity.Debt;
import ee.taltech.debty.entity.Person;
import ee.taltech.debty.model.DebtStatus;
import ee.taltech.debty.repository.DebtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DebtService {

    private final DebtRepository debtRepository;
    private final UserService userService;

    public Debt saveDebt(Debt debt) {
        if (debt.getPayer().getId() == null) userService.saveUser(debt.getPayer());
        if (debt.getClosedAt() == null) {
            if (debt.getStatus() == DebtStatus.CONFIRMED || debt.getStatus() == DebtStatus.DECLINED) {
                debt.setClosedAt(LocalDateTime.now());
            }
        }
        debt.setModifiedAt(LocalDateTime.now());
        return debtRepository.save(debt);
    }

    void saveDebts(List<Debt> debts) {
        debts.forEach(this::saveDebt);
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

    public BigDecimal getTotalDebtBalanceForUser(Long userId) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Debt debt : getAllDebtsByUserId(userId)) {
            if (userId.equals(debt.getPayer().getId())) {
                sum = sum.subtract(debt.getSum());
            } else {
                sum = sum.add(debt.getSum());
            }
        }
        return sum;
    }
}
