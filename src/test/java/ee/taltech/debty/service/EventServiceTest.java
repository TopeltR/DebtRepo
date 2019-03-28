package ee.taltech.debty.service;

import ee.taltech.debty.entity.Event;
import ee.taltech.debty.entity.Person;
import ee.taltech.debty.repository.EventRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    private Person person = Person.builder().firstName("Bob").lastName("Builder")
            .id(3L).build();
    private Person person2 = Person.builder().firstName("Kalevi").lastName("Poeg").id(4L).build();

    @Test
    public void saveEvent_shouldSaveEvent() {
        Event event = new Event();
        event.setTitle("Test event 1");
        event.setId(1L);
        event.setOwner(person);
        event.setPeople(Arrays.asList(person,person2));
        eventService.saveEvent(event);
        verify(eventRepository).save(event);
    }

    @Test
    public void getAllEvents_shouldReturnTwoEvents() {
        Event event = new Event();
        event.setTitle("Test event 1");
        event.setId(1L);
        Event event1 = new Event();
        event.setTitle("Test event 2");
        event.setId(2L);

        eventService.saveEvent(event);
        eventService.saveEvent(event1);
        when(eventRepository.findAll()).thenReturn(Arrays.asList(event,event1));
        assertEquals(eventService.getAllEvents(),Arrays.asList(event,event1));
    }

    @Test
    public void getEventById_shouldReturnEvent() {
        Event event = new Event();
        event.setTitle("Test event 1");
        event.setId(1L);

        eventService.saveEvent(event);
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        assertEquals(eventService.getEventById(1L), Optional.of(event));
    }

    @Test
    public void getAllEventsByUserId_shouldReturnTwoEvents() {
        Event event = new Event();
        event.setTitle("Test event 1");
        event.setId(1L);
        event.setOwner(person);

        eventService.saveEvent(event);

    }



}
