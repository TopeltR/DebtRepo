package ee.taltech.debty.controller;

import ee.taltech.debty.entity.Debt;
import ee.taltech.debty.service.DebtService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DebtController {

    private final DebtService debtService;

    public DebtController(DebtService debtService) {
        this.debtService = debtService;
    }

    @PostMapping("/debts")
    public Debt saveDebt(@RequestBody Debt debt) {
        return debtService.saveDebt(debt);
    }

    @GetMapping("/debts/all")
    public List<Debt> getAllDebts() {
        return debtService.getAllDebts();
    }

    @GetMapping("/debts/{id}")
    public Debt getDebtById(@PathVariable("id") Long id) {
        return debtService.getDebtById(id).orElseGet(Debt::new);
    }

    @GetMapping("/debts/user/{userId}")
    public List<Debt> getAllDebtsByUserId(@PathVariable("userId") Long userId) {
        return debtService.getAllDebtsByUserId(userId);
    }

    @DeleteMapping("/debts/{id}")
    public void deleteDebt(@PathVariable("id") Long id) {
        debtService.deleteDebt(id);
    }
}
