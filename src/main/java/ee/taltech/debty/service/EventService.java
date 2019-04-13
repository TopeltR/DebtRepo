package ee.taltech.debty.service;

import ee.taltech.debty.entity.Bill;
import ee.taltech.debty.entity.Debt;
import ee.taltech.debty.entity.Event;
import ee.taltech.debty.entity.Person;
import ee.taltech.debty.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class EventService {

    private final UserService userService;
    private final DebtService debtService;
    private final BillService billService;
    private final EventRepository eventRepository;
    private final DebtDistributionService debtDistributionService;

    public Event saveEvent(Event event) {
        event.setModifiedAt(now());
        return eventRepository.save(event);
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public void deleteEventById(Long id) {
        Optional<Event> eventOptional = getEventById(id);
        eventOptional.ifPresent(eventRepository::delete);
    }

    public List<Event> getAllEventsByUserId(Long userId) {
        Optional<Person> userOptional = userService.getUserById(userId);
        if (userOptional.isPresent()) return eventRepository.findAllByPeopleContaining(userOptional.get());
        else return new ArrayList<>();
    }

    public List<Debt> calculateDistributedDebts(Long eventId) {
        Event event = getEventById(eventId).orElseGet(Event::new);
        if (!eventId.equals(event.getId())) return null;
        List<Debt> debts = debtDistributionService.calculateDebtDistribution(event);
        debts.forEach(debt -> debt.setTitle(String.format("Debt from event \"%s\"", event.getTitle())));
        return debts;
    }

    public Event closeEventAndSaveNewDebts(Long id, String email) {
        Event event = getEventById(id).orElseGet(Event::new);
        if (event.getOwner() != null && event.getOwner().getEmail().equals(email)) {
            List<Debt> distributedDebts = calculateDistributedDebts(id);
            event.setClosedAt(now());
            if (distributedDebts != null) {
                debtService.saveDebts(distributedDebts);
            }
            event = saveEvent(event);
        }
        return event;
    }

    public Event addOrUpdateBill(Long eventId, Bill bill) {
        Optional<Event> eventOptional = removeBillFromEvent(eventId, bill.getId());
        if (!eventOptional.isPresent()) return null;

        Event event = eventOptional.get();
        bill.setModifiedAt(now());
        event.setModifiedAt(now());
        event.getBills().add(bill);
        return saveEvent(event);
    }

    public void deleteBillFromEvent(Long eventId, Long billId) {
        removeBillFromEvent(eventId, billId);
        billService.deleteBillById(billId);
    }

    private Optional<Event> removeBillFromEvent(Long eventId, Long billId) {
        Optional<Event> eventById = getEventById(eventId);
        if (!eventById.isPresent()) return Optional.empty();
        Event event = eventById.get();
        Optional<Bill> billOptional = event.getBills().stream().filter(b -> b.getId().equals(billId)).findFirst();
        if (billOptional.isPresent()) {
            Bill b = billOptional.get();
            event.getBills().remove(b);
            event.setModifiedAt(now());
        }
        return Optional.of(event);
    }
}
