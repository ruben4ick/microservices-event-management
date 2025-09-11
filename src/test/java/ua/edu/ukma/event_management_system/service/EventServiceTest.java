package ua.edu.ukma.event_management_system.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ua.edu.ukma.event_management_system.domain.Event;
import ua.edu.ukma.event_management_system.dto.EventDto;
import ua.edu.ukma.event_management_system.service.interfaces.EventService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class EventServiceTest {

    @Autowired
    private EventService eventService;

    @Order(1)
    @Test
    void testGetAllEvents() {
        List<Event> events = eventService.getAllEvents();
        assertEquals(3, events.size(), "Expected three events in the database.");
    }

    @Order(2)
    @Test
    void testGetEventById() {
        Event event = eventService.getEventById(1L); // Assuming ID of 1 is from the populated data
        assertNotNull(event, "Event with ID 1 should exist.");
        assertEquals("queen-concert", event.getEventTitle(), "Event title should match.");
    }

    @Order(3)
    @Test
    void testCreateEvent() {
        EventDto newEvent = new EventDto();
        newEvent.setEventTitle("New Event");
        newEvent.setDateTimeStart(LocalDateTime.now().plusDays(1));
        newEvent.setDateTimeEnd(LocalDateTime.now().plusDays(1).plusHours(2));
        newEvent.setBuilding(1L); // Assuming building with ID 1 exists
        newEvent.setDescription("A new exciting event");
        newEvent.setNumberOfTickets(200);
        newEvent.setMinAgeRestriction(18);
        newEvent.setPrice(150);
        newEvent.setCreator(2);

        eventService.createEvent(newEvent);

        List<Event> events = eventService.getAllEvents();
        assertEquals(4, events.size(), "Expected one more event after creation.");
        assertTrue(events.stream().anyMatch(e -> e.getEventTitle().equals("New Event")),
                "The new event should be present in the database.");
    }

    @Order(4)
    @Test
    void testUpdateEvent() {
        EventDto updatedEvent = new EventDto();
        updatedEvent.setEventTitle("Updated Event Title");
        updatedEvent.setDateTimeStart(LocalDateTime.now().plusDays(2));
        updatedEvent.setDateTimeEnd(LocalDateTime.now().plusDays(2).plusHours(3));
        updatedEvent.setBuilding(2L); // Assuming building with ID 2 exists
        updatedEvent.setDescription("Updated description");
        updatedEvent.setNumberOfTickets(300);
        updatedEvent.setMinAgeRestriction(21);
        updatedEvent.setPrice(200);

        eventService.updateEvent(1L, updatedEvent); // Assuming ID 1 exists

        Event updated = eventService.getEventById(1L);
        assertEquals("Updated Event Title", updated.getEventTitle(), "Event title should be updated.");
        assertEquals(300, updated.getNumberOfTickets(), "Number of tickets should be updated.");
    }

    @Order(5)
    @Test
    void testDeleteEvent() {
        eventService.deleteEvent(1L);

        List<Event> events = eventService.getAllEvents();
        assertEquals(3, events.size(), "Expected one less event after deletion.");
        assertFalse(events.stream().anyMatch(e -> e.getId() == 1L),
                "Event with ID 1 should no longer exist.");
    }
}
