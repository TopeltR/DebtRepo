package ee.taltech.debty.controller;

import ee.taltech.debty.entity.Debt;
import ee.taltech.debty.service.DebtService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController("/debts")
public class DebtController {

    private final DebtService debtService;

    public DebtController(DebtService debtService) {
        this.debtService = debtService;
    }

    @PostMapping("/")
    public Debt saveDebt(@RequestBody Debt debt) {
        return debtService.saveDebt(debt);
    }

    @GetMapping("/all")
    public List<Debt> getAllDebts() {
        return debtService.getAllDebts();
    }

    @GetMapping("/{id}")
    public Debt getDebtById(@PathVariable("id") Long id) {
        return debtService.getDebtById(id).orElseGet(Debt::new);
    }

    @GetMapping("/user/{userId}")
    public List<Debt> getAllDebtsByUserId(@PathVariable("userId") Long userId) {
        return debtService.getAllDebtsByUserId(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteDebt(@PathVariable("id") Long id) {
        debtService.deleteDebt(id);
    }

    @GetMapping("/user/{userId}/total")
    public BigDecimal getTotalDebtBalance(@PathVariable("userId") Long userId) {
        return debtService.getTotalDebtBalanceForUser(userId);
    }
}
