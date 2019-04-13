package ee.taltech.debty.controller;

import ee.taltech.debty.entity.Debt;
import ee.taltech.debty.service.DebtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/debts")
public class DebtController {

    private final DebtService debtService;

    @PostMapping("/")
    @PreAuthorize("@permissionEvaluator.isUserByEmail(#debt.owner.email)")
    public Debt saveDebt(@RequestBody Debt debt) {
        return debtService.saveDebt(debt);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@permissionEvaluator.isDebtParticipantById(#id)")
    public Debt getDebtById(@PathVariable("id") Long id) {
        return debtService.getDebtById(id).orElseGet(Debt::new);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("@permissionEvaluator.isUserById(#userId)")
    public List<Debt> getAllDebtsByUserId(@PathVariable("userId") Long userId) {
        return debtService.getAllDebtsByUserId(userId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@permissionEvaluator.isDebtOwnerById(#id)")
    public void deleteDebt(@PathVariable("id") Long id) {
        debtService.deleteDebt(id);
    }

    @GetMapping("/user/{userId}/total")
    @PreAuthorize("@permissionEvaluator.isUserById(#userId)")
    public BigDecimal getTotalDebtBalance(@PathVariable("userId") Long userId) {
        return debtService.getTotalDebtBalanceForUser(userId);
    }
}
