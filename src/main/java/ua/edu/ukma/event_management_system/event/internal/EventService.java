package ua.edu.ukma.event_management_system.event.internal;

import org.springframework.stereotype.Service;

/**
 * Simple event service for the Event module.
 */
@Service
public class EventService {

    public Event createEvent(EventDto eventDto) {
        // Simple implementation - just create an event object
        Event event = new Event();
        event.setId((int) System.currentTimeMillis()); // Simple ID generation
        event.setEventTitle(eventDto.getEventTitle());
        event.setDateTimeStart(eventDto.getDateTimeStart());
        event.setDateTimeEnd(eventDto.getDateTimeEnd());
        event.setDescription(eventDto.getDescription());
        event.setNumberOfTickets(eventDto.getNumberOfTickets());
        event.setMinAgeRestriction(eventDto.getMinAgeRestriction());
        event.setPrice(eventDto.getPrice());
        
        // Create simple building and user objects
        ua.edu.ukma.event_management_system.building.internal.Building building = 
            new ua.edu.ukma.event_management_system.building.internal.Building();
        building.setId((int) eventDto.getBuilding());
        event.setBuilding(building);
        
        ua.edu.ukma.event_management_system.user.internal.User creator = 
            new ua.edu.ukma.event_management_system.user.internal.User();
        creator.setId(eventDto.getCreator());
        event.setCreator(creator);
        
        return event;
    }
}
