package ee.taltech.debty.controller;

import ee.taltech.debty.entity.Bill;
import ee.taltech.debty.entity.Debt;
import ee.taltech.debty.entity.Event;
import ee.taltech.debty.service.BillService;
import ee.taltech.debty.service.EventService;
import ee.taltech.debty.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final BillService billService;

    @GetMapping("/events/all")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping("/events")
    public Event addEvent(@RequestBody Event event) {
        return eventService.saveEvent(event);
    }

    @GetMapping("/events/{id}")
    public Event getEventById(@PathVariable("id") Long id) {
        return eventService.getEventById(id).orElseGet(Event::new);
    }

    @PostMapping("/events/{id}/bills")
    public Event addBill(@PathVariable("id") Long id, @RequestBody Bill bill) {
        return eventService.addOrUpdateBill(id, bill);
    }

    @GetMapping("/events/user/{userId}")
    public List<Event> getAllEventsByUserId(@PathVariable("userId") Long userId) {
        return eventService.getAllEventsByUserId(userId);
    }

    @GetMapping("/events/{eventId}/debts")
    public List<Debt> getDistributedDebtCalculation(@PathVariable("eventId") Long eventId) {
        return eventService.calculateDistributedDebts(eventId);
    }

    @PostMapping("/events/{eventId}/close")
    public void closeEvent(@PathVariable("eventId") Long eventId, Principal principal) {
        eventService.closeEventAndSaveNewDebts(eventId, principal.getName());
    }
}
