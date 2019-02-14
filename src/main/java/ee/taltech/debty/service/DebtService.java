package ee.taltech.debty.service;

import ee.taltech.debty.entity.Debt;
import ee.taltech.debty.entity.User;
import ee.taltech.debty.repository.DebtRepository;
import org.springframework.stereotype.Service;

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
        return debtRepository.save(debt);
    }

    public List<Debt> getAllDebts() {
        return debtRepository.findAll();
    }

    public Optional<Debt> getDebtById(Long id) {
        return debtRepository.findById(id);
    }

    public List<Debt> getAllDebtsByUserId(Long userId) {
        Optional<User> userOptional = userService.getUserById(userId);
        if (userOptional.isPresent()) return debtRepository.findAllByUserParticipating(userOptional.get());
        else return new ArrayList<>();
    }
}
