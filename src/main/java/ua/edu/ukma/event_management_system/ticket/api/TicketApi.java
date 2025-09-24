package ua.edu.ukma.event_management_system.ticket.api;

import ua.edu.ukma.event_management_system.ticket.internal.TicketDto;
import ua.edu.ukma.event_management_system.ticket.internal.Ticket;

import java.util.List;

public interface TicketApi {

    List<Ticket> getAllTickets();

    Ticket getTicketById(Long ticketId);

    Ticket purchaseTicket(TicketDto ticketDto);

    List<Ticket> getTicketsByUser(Long userId);

    void cancelTicket(Long ticketId);
}
