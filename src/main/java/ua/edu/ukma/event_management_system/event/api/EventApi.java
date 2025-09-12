package ua.edu.ukma.event_management_system.event.api;

import ua.edu.ukma.event_management_system.shared.api.EventDto;
import ua.edu.ukma.event_management_system.event.internal.Event;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Public API for Event module.
 * This interface defines the external contract for event operations.
 */
public interface EventApi {
    
    /**
     * Get all events.
     * @return list of all events
     */
    List<Event> getAllEvents();
    
    /**
     * Get event by ID.
     * @param eventId the event ID
     * @return the event
     */
    Event getEventById(Long eventId);
    
    /**
     * Create a new event.
     * @param eventDto the event data
     * @return the created event
     */
    Event createEvent(EventDto eventDto);
    
    /**
     * Update an existing event.
     * @param eventId the event ID
     * @param eventDto the updated event data
     * @return the updated event
     */
    Event updateEvent(Long eventId, EventDto eventDto);
    
    /**
     * Delete an event.
     * @param eventId the event ID
     */
    void deleteEvent(Long eventId);
    
    /**
     * Get events by title.
     * @param title the event title
     * @return list of matching events
     */
    List<Event> getEventsByTitle(String title);
    
    /**
     * Get events that intersect with the given time range.
     * @param start the start time
     * @param end the end time
     * @param buildingId the building ID (optional, null for all buildings)
     * @return list of intersecting events
     */
    List<Event> getEventsInTimeRange(LocalDateTime start, LocalDateTime end, Long buildingId);
    
    /**
     * Get events for a specific organizer.
     * @param organizerId the organizer ID
     * @return list of events created by the organizer
     */
    List<Event> getEventsByOrganizer(Long organizerId);
}
