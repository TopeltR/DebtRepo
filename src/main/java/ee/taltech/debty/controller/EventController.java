package ee.taltech.debty.controller;

import ee.taltech.debty.entity.Bill;
import ee.taltech.debty.entity.Debt;
import ee.taltech.debty.entity.Event;
import ee.taltech.debty.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

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

    @DeleteMapping("/events/{id}")
    public void deleteEventById(@PathVariable("id") Long id) {
        eventService.deleteEventById(id);
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
    public Event closeEvent(@PathVariable("eventId") Long eventId, Principal principal) {
        return eventService.closeEventAndSaveNewDebts(eventId, principal.getName());
    }
}
