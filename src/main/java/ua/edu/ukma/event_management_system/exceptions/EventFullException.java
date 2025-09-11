package ua.edu.ukma.event_management_system.exceptions;

public class EventFullException extends RuntimeException {
    public EventFullException(String error) {
        super(error);
    }
}
