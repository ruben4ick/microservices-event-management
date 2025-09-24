package ua.edu.ukma.event_management_system.event.api;

import ua.edu.ukma.event_management_system.event.internal.EventDto;
import ua.edu.ukma.event_management_system.event.internal.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventApi {

    List<Event> getAllEvents();

    Event getEventById(Long eventId);

    Event createEvent(EventDto eventDto);

    Event updateEvent(Long eventId, EventDto eventDto);

    void deleteEvent(Long eventId);
}
