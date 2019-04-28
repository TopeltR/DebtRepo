package ee.taltech.debty.security.service;

import ee.taltech.debty.entity.Person;
import ee.taltech.debty.service.BillService;
import ee.taltech.debty.service.DebtService;
import ee.taltech.debty.service.EventService;
import ee.taltech.debty.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionEvaluator {

    private final UserService userService;
    private final BillService billService;
    private final DebtService debtService;
    private final EventService eventService;
    private final SecurityService securityService;

    public boolean isUserByEmail(String email) {
        return email.equals(securityService.findLoggedInUsername());
    }

    public boolean isUserById(Long id) {
        return userService.getUserById(id).map(person -> isUserByEmail(person.getEmail())).orElse(false);
    }

    public boolean isEventOwnerById(Long eventId) {
        return eventService.getEventById(eventId).map(event -> isUserByEmail(event.getOwner().getEmail())).orElse(false);
    }

    public boolean isBillOwnerById(Long billId) {
        return billService.getBillById(billId).map(bill -> isUserByEmail(bill.getCreator().getEmail())).orElse(false);
    }

    public boolean isDebtOwnerById(Long debtId) {
        return debtService.getDebtById(debtId).map(debt -> isUserByEmail(debt.getOwner().getEmail())).orElse(false);
    }

    public boolean isDebtParticipantById(Long debtId) {
        return debtService.getDebtById(debtId).map(debt ->
                isUserByEmail(debt.getReceiver().getEmail()) || isUserByEmail(debt.getPayer().getEmail())
        ).orElse(false);
    }

    private boolean isInList(List<Person> people) {
        return people.contains(userService.getUserByEmail(securityService.findLoggedInUsername()).orElse(new Person()));
    }

    public boolean isInEventPeople(Long eventId) {
        return eventService.getEventById(eventId).map(event -> isInList(event.getPeople())).orElse(false);
    }

    public boolean isNotDebtOwner(Long debtId) {
        return debtService.getDebtById(debtId).map(debt ->
                isUserByEmail((debt.getOwner() == debt.getPayer() ? debt.getReceiver() : debt.getPayer())
                        .getEmail())).orElse(false);
    }

    public boolean isDebtPayer(Long debtId) {
        return debtService.getDebtById(debtId).map(debt -> isUserByEmail(debt.getPayer().getEmail())).orElse(false);
    }

    public boolean isDebtReceiver(Long debtId) {
        return debtService.getDebtById(debtId).map(debt -> isUserByEmail(debt.getReceiver().getEmail())).orElse(false);
    }
}
