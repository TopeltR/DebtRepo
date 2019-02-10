package ee.taltech.debty.service;

import ee.taltech.debty.entity.Debt;
import ee.taltech.debty.repository.DebtRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DebtService {
    private final DebtRepository debtRepository;

    public DebtService(DebtRepository debtRepository) {
        this.debtRepository = debtRepository;
    }

    public Debt saveDebt(Debt debt) {
        return debtRepository.save(debt);
    }

    public List<Debt> getAllDebts() {
        return debtRepository.findAll();
    }

    public Optional<Debt> getDebtById(Long id) {
        return debtRepository.findById(id);
    }
}
