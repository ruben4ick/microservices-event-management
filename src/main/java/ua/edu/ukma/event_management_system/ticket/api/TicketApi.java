package ua.edu.ukma.event_management_system.ticket.api;

import ua.edu.ukma.event_management_system.ticket.internal.TicketDto;
import ua.edu.ukma.event_management_system.ticket.internal.Ticket;

import java.util.List;

/**
 * Public API for Ticket module.
 * This interface defines the external contract for ticket operations.
 */
public interface TicketApi {
    
    /**
     * Get all tickets.
     * @return list of all tickets
     */
    List<Ticket> getAllTickets();
    
    /**
     * Get ticket by ID.
     * @param ticketId the ticket ID
     * @return the ticket
     */
    Ticket getTicketById(Long ticketId);
    
    /**
     * Purchase a ticket.
     * @param ticketDto the ticket data
     * @return the purchased ticket
     */
    Ticket purchaseTicket(TicketDto ticketDto);
    
    /**
     * Get tickets for a specific user.
     * @param userId the user ID
     * @return list of user's tickets
     */
    List<Ticket> getTicketsByUser(Long userId);
    
    /**
     * Get tickets for a specific event.
     * @param eventId the event ID
     * @return list of event tickets
     */
    List<Ticket> getTicketsByEvent(Long eventId);
    
    /**
     * Cancel a ticket.
     * @param ticketId the ticket ID
     */
    void cancelTicket(Long ticketId);
    
    /**
     * Validate a ticket.
     * @param ticketId the ticket ID
     * @return true if ticket is valid, false otherwise
     */
    boolean validateTicket(Long ticketId);
}
