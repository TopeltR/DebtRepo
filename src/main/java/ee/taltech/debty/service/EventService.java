package ee.taltech.debty.service;

import ee.taltech.debty.entity.Debt;
import ee.taltech.debty.entity.Event;
import ee.taltech.debty.entity.Person;
import ee.taltech.debty.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final UserService userService;
    private final EventRepository eventRepository;
    private final DebtDistributionService debtDistributionService;

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public List<Event> getAllEventsByUserId(Long userId) {
        Optional<Person> userOptional = userService.getUserById(userId);
        if (userOptional.isPresent()) return eventRepository.findAllByPeopleContaining(userOptional.get());
        else return new ArrayList<>();
    }

    public List<Debt> calculateDistributedDebts(Long eventId) {
        Event event = getEventById(eventId).orElseGet(Event::new);
        if (!eventId.equals(event.getId())) return null;
        return debtDistributionService.calculateDebtDistribution(event);
    }
}
