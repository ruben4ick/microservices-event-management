package ua.edu.ukma.event_management_system.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ua.edu.ukma.event_management_system.domain.Event;
import ua.edu.ukma.event_management_system.domain.Ticket;
import ua.edu.ukma.event_management_system.dto.EventDto;
import ua.edu.ukma.event_management_system.dto.TicketDto;
import ua.edu.ukma.event_management_system.dto.UserDto;
import ua.edu.ukma.event_management_system.entity.EventEntity;
import ua.edu.ukma.event_management_system.entity.TicketEntity;
import ua.edu.ukma.event_management_system.entity.UserEntity;
import ua.edu.ukma.event_management_system.exceptions.EventFullException;
import ua.edu.ukma.event_management_system.repository.TicketRepository;
import ua.edu.ukma.event_management_system.service.interfaces.EventService;
import ua.edu.ukma.event_management_system.service.interfaces.TicketService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private ModelMapper modelMapper;
    private EventService eventService;
    private TicketRepository ticketRepository;

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    void setModelMapper(@Lazy ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    void setTicketRepository(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket createTicket(TicketDto ticket) {
        TicketEntity ticketEntity = dtoToEntity(ticket);
        EventEntity event = ticketEntity.getEvent();
        if (event.getNumberOfTickets() <= event.getUsers().size()) {
            throw new EventFullException("Event(id=" + event.getId() + ", name=" + event.getEventTitle() + ") is already full");
        }
        return toDomain(ticketRepository.save(dtoToEntity(ticket)));
    }

    @Override
    public Ticket purchaseTicket(UserDto user, EventDto event, int price) {
        TicketEntity ticket = new TicketEntity(dtoToEntity(user), toEntity(event), price, LocalDateTime.now());
        return toDomain(ticketRepository.save(ticket));
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll()
                .stream().map(this::toDomain).toList();
    }

    @Override
    public Ticket getTicketById(long ticketId) {
        return toDomain(ticketRepository.findById(ticketId).orElse(null));
    }

    @Override
    public void updateTicket(long id, TicketDto updatedTicket) {
        Optional<TicketEntity> existingUserOpt = ticketRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            TicketEntity existingTicket = existingUserOpt.get();
            Event updatedEvent = eventService.getEventById(updatedTicket.getEvent());
            existingTicket.setEvent(toEntity(updatedEvent));
            existingTicket.setPrice(updatedTicket.getPrice());
            existingTicket.setPurchaseDate(updatedTicket.getPurchaseDate());
            ticketRepository.save(existingTicket);
        }
    }

    @Override
    public void removeTicket(long ticketId) {
        ticketRepository.deleteById(ticketId);
    }

    @Override
    public List<Ticket> getAllTicketsForUser(UserDto user) {
        return ticketRepository.findAllByUserId((int) user.getId())
                .stream().map(this::toDomain).toList();
    }

    @Override
    public List<Ticket> getAllTicketsForUser(String name) {
        return ticketRepository.findAllByUserUsername(name).stream().map(this::toDomain).toList();
    }

    @Override
    public List<Long> getAllTicketsCreatedToday() {
        return ticketRepository.findAllCreatedToday();
    }

    @Override
    public List<Ticket> getAllTicketsCreatedByUser(long user) {
        return ticketRepository.findTicketEntitiesByEvent_Creator_Id(user)
                .stream().map(this::toDomain).toList();
    }

    private Ticket toDomain(TicketEntity ticketEntity) {
        return modelMapper.map(ticketEntity, Ticket.class);
    }


    private TicketEntity dtoToEntity(TicketDto ticketDto) {
        Ticket tick = modelMapper.map(ticketDto, Ticket.class);
        return modelMapper.map(tick, TicketEntity.class);
    }

    private UserEntity dtoToEntity(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }

    private EventEntity toEntity(EventDto event) {
        return modelMapper.map(event, EventEntity.class);
    }

    private EventEntity toEntity(Event event) {
        return modelMapper.map(event, EventEntity.class);
    }

}
