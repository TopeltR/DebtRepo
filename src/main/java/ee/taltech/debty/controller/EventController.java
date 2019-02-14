package ee.taltech.debty.controller;

import ee.taltech.debty.entity.Event;
import ee.taltech.debty.service.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

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

    @GetMapping("/events/user/{userId}")
    public List<Event> getAllEventsByUserId(@PathVariable("userId") Long userId) {
        return eventService.getAllEventsByUserId(userId);
    }
}
