package ua.edu.ukma.event_management_system.service.interfaces;

import ua.edu.ukma.event_management_system.domain.Ticket;
import ua.edu.ukma.event_management_system.dto.EventDto;
import ua.edu.ukma.event_management_system.dto.TicketDto;
import ua.edu.ukma.event_management_system.dto.UserDto;

import java.util.List;

public interface TicketService {
	Ticket createTicket(TicketDto ticket);

	Ticket purchaseTicket(UserDto user, EventDto event, int price);

	List<Ticket> getAllTickets();

	Ticket getTicketById(long ticketId);

	void updateTicket(long id, TicketDto updatedTicket);

	void removeTicket(long ticketId);

	List<Ticket> getAllTicketsForUser(UserDto user);

	List<Ticket> getAllTicketsForUser(String name);

	List<Long> getAllTicketsCreatedToday();

	List<Ticket> getAllTicketsCreatedByUser(long user);
}
