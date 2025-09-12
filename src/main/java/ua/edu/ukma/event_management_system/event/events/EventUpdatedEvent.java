package ua.edu.ukma.event_management_system.event.events;

import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

/**
 * Event published when an event is updated.
 */
public class EventUpdatedEvent extends ApplicationEvent {
    
    private final Long eventId;
    private final String eventTitle;
    private final Long buildingId;
    private final LocalDateTime startTime;
    private final int numberOfTickets;
    
    public EventUpdatedEvent(Object source, Long eventId, String eventTitle, Long buildingId, 
                           LocalDateTime startTime, int numberOfTickets) {
        super(source);
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.buildingId = buildingId;
        this.startTime = startTime;
        this.numberOfTickets = numberOfTickets;
    }
    
    public Long getEventId() {
        return eventId;
    }
    
    public String getEventTitle() {
        return eventTitle;
    }
    
    public Long getBuildingId() {
        return buildingId;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public int getNumberOfTickets() {
        return numberOfTickets;
    }
}
