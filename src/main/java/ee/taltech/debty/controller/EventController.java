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
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @GetMapping("/all")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping("/")
    public Event addEvent(@RequestBody Event event) {
        return eventService.saveEvent(event);
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable("id") Long id) {
        return eventService.getEventById(id).orElseGet(Event::new);
    }

    @DeleteMapping("/{id}")
    public void deleteEventById(@PathVariable("id") Long id) {
        eventService.deleteEventById(id);
    }

    @PostMapping("/{id}/bills")
    public Event addBill(@PathVariable("id") Long id, @RequestBody Bill bill) {
        return eventService.addOrUpdateBill(id, bill);
    }

    @DeleteMapping("/{id}/bills/{billId}")
    public void addBill(@PathVariable("id") Long id, @PathVariable("billId") Long billId) {
        eventService.deleteBillFromEvent(id, billId);
    }

    @GetMapping("/user/{userId}")
    public List<Event> getAllEventsByUserId(@PathVariable("userId") Long userId) {
        return eventService.getAllEventsByUserId(userId);
    }

    @GetMapping("/{eventId}/debts")
    public List<Debt> getDistributedDebtCalculation(@PathVariable("eventId") Long eventId) {
        return eventService.calculateDistributedDebts(eventId);
    }

    @PostMapping("/{eventId}/close")
    public Event closeEvent(@PathVariable("eventId") Long eventId, Principal principal) {
        return eventService.closeEventAndSaveNewDebts(eventId, principal.getName());
    }
}
