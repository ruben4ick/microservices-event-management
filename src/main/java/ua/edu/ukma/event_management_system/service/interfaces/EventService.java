package ua.edu.ukma.event_management_system.service.interfaces;

import ua.edu.ukma.event_management_system.domain.Event;
import ua.edu.ukma.event_management_system.dto.EventDto;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<Event> getAllEvents();

    void createEvent(EventDto event);

    Event getEventById(long eventId);

    List<Event> getAllEventsByTitle(String title);

    void updateEvent(Long id, EventDto updatedEvent);

    void deleteEvent(long eventId);

    List<Event>getAllRelevant();

    List<Event> getAllForOrganizer(Long organizerId);

    List<Event> getAllThatIntersect(LocalDateTime start, LocalDateTime end, long id);
}
