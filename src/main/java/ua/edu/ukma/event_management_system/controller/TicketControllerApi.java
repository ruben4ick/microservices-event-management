package ua.edu.ukma.event_management_system.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.*;
import ua.edu.ukma.event_management_system.domain.Event;
import ua.edu.ukma.event_management_system.domain.Ticket;
import ua.edu.ukma.event_management_system.dto.TicketDto;
import ua.edu.ukma.event_management_system.exceptions.EventFullException;
import ua.edu.ukma.event_management_system.service.interfaces.EventService;
import ua.edu.ukma.event_management_system.service.interfaces.TicketService;

import java.util.List;

@RestController
@RequestMapping("api/ticket")
@ConditionalOnExpression("${api.ticket.enable}")
public class TicketControllerApi {

	private ModelMapper modelMapper;
	private TicketService ticketService;
	private EventService eventService;

	@Autowired
	public void setModelWrapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Autowired
	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	@Autowired
	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

	@GetMapping("/{id}")
	public TicketDto getTicket(@PathVariable long id) {
		return toDto(ticketService.getTicketById(id));
	}

	@GetMapping
	public List<TicketDto> getTickets() {
		return ticketService.getAllTickets()
				.stream()
				.map(this::toDto)
				.toList();
	}

	@PostMapping
	public void createTicket(@RequestBody @Valid TicketDto ticketDto) throws EventFullException {
		Event event = eventService.getEventById(ticketDto.getId());
		if (event.getUsers().size() >= event.getNumberOfTickets()) {
			throw new EventFullException("Event is full!");
		}
		ticketService.createTicket(ticketDto);
	}

	@PutMapping("/{id}")
	public void updateTicket(@PathVariable long id, @RequestBody @Valid TicketDto ticketDto) {
		ticketService.updateTicket(id, ticketDto);
	}

	@DeleteMapping("/{id}")
	public void deleteTicket(@PathVariable long id) {
		ticketService.removeTicket(id);
	}

	private TicketDto toDto(Ticket ticket) {
		return modelMapper.map(ticket, TicketDto.class);
	}
}
