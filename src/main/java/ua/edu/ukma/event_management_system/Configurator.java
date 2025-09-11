package ua.edu.ukma.event_management_system;

import org.modelmapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.edu.ukma.event_management_system.domain.*;
import ua.edu.ukma.event_management_system.dto.BuildingDto;
import ua.edu.ukma.event_management_system.dto.EventDto;
import ua.edu.ukma.event_management_system.dto.TicketDto;
import ua.edu.ukma.event_management_system.entity.*;
import ua.edu.ukma.event_management_system.service.interfaces.BuildingService;
import ua.edu.ukma.event_management_system.service.interfaces.EventService;
import ua.edu.ukma.event_management_system.service.interfaces.UserService;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Configurator {

    private static final Logger logger = LoggerFactory.getLogger(Configurator.class);

    private BuildingService buildingService;
    private EventService eventService;
    private UserService userService;

    @Autowired
    public void setBuildingService(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Primary
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapperResult = new ModelMapper();

        // Event: domain to dto
        TypeMap<Event, EventDto> eventMapper = mapperResult.createTypeMap(Event.class, EventDto.class);
        eventMapper.addMappings(mapper -> mapper.map(src -> src.getBuilding().getId(), EventDto::setBuilding));
        eventMapper.addMappings(mapper -> mapper.map(src -> src.getCreator().getId(), EventDto::setCreator));
        logger.info("Created Event mappings");

        // Event: dto to entity
        Converter<Long, BuildingEntity> toBuildingConverter = ctx -> mapperResult.map(buildingService.getBuildingById(ctx.getSource()), BuildingEntity.class);
        Converter<Long, UserEntity> toUserConverter = ctx -> mapperResult.map(userService.getUserById(ctx.getSource()), UserEntity.class);
        TypeMap<EventDto, EventEntity> eventMapperRev = mapperResult.createTypeMap(EventDto.class, EventEntity.class);
        eventMapperRev.addMappings(mapper -> mapper.using(toBuildingConverter).map(EventDto::getBuilding, EventEntity::setBuilding));
        eventMapperRev.addMappings(mapper -> mapper.using(toUserConverter).map(EventDto::getCreator, EventEntity::setCreator));

        logger.info("Created Event reverse mappings");

        // Building: domain to dto
        Converter<List<BuildingRating>, List<Long>> ratingConverter = ctx -> ctx.getSource() == null
                ? new ArrayList<>()
                : ctx.getSource()
                .stream()
                .map(BuildingRating::getId)
                .toList();

        TypeMap<Building, BuildingDto> buildingMapper = mapperResult.createTypeMap(Building.class, BuildingDto.class);
        buildingMapper.addMappings(mapper -> mapper.using(ratingConverter).map(Building::getRating, BuildingDto::setRating));



        logger.info("Created Building mappings");

        // Building: entity to domain (nested ratings)
        TypeMap<BuildingRatingEntity, BuildingRating> breToDomain = mapperResult.createTypeMap(BuildingRatingEntity.class, BuildingRating.class);
        breToDomain.addMappings(mapper -> mapper.map(src -> src.getBuilding().getId(), BuildingRating::setBuilding));

        TypeMap<BuildingEntity, Building> buildingEntityToBuilding = mapperResult.createTypeMap(BuildingEntity.class, Building.class);
        buildingEntityToBuilding.addMappings(mapper ->
                mapper.map(
                        src -> src.getRating() == null
                                ? new ArrayList<>()
                                : src.getRating()
                                .stream()
                                .map(ratingEntity -> mapperResult.map(ratingEntity, BuildingRating.class))
                                .toList(),
                        Building::setRating
                )
        );


        logger.info("Created BuildingEntity to Building mappings");

        // Building: dto to model (nested ratings)
        Converter<Long, BuildingRating> toRatingConverter = ctx -> mapperResult.map(buildingService.getRatingById(ctx.getSource()), BuildingRating.class);
        TypeMap<BuildingDto, Building> buildingDtoToDomain = mapperResult.createTypeMap(BuildingDto.class, Building.class);
        buildingDtoToDomain.addMappings(mapper -> mapper.using(toRatingConverter).map(BuildingDto::getRating, Building::setRating));

        logger.info("Created BuildingDto to Building mappings");

        // Ticket: domain to dto
        TypeMap<Ticket, TicketDto> ticketToDto = mapperResult.createTypeMap(Ticket.class, TicketDto.class);
        ticketToDto.addMappings(mapper -> mapper.map(src -> src.getEvent().getId(), TicketDto::setEvent));
        ticketToDto.addMappings(mapper -> mapper.map(src -> src.getUser().getId(), TicketDto::setUser));

        logger.info("Created Ticket mappings");

        // Ticket: dto to domain
        Converter<Long, Event> idToEvent = ctx -> eventService.getEventById(ctx.getSource());
        Converter<Long, User> idToUser = ctx -> userService.getUserById(ctx.getSource());
        TypeMap<TicketDto, Ticket> ticketDtoToDomain = mapperResult.createTypeMap(TicketDto.class, Ticket.class);
        ticketDtoToDomain.addMappings(mapper -> mapper.using(idToEvent).map(TicketDto::getEvent, Ticket::setEvent));
        ticketDtoToDomain.addMappings(mapper -> mapper.using(idToUser).map(TicketDto::getUser, Ticket::setUser));

        logger.info("Created TicketDto to Ticket mappings");

        // Ticket: domain to entity
        mapperResult.typeMap(Ticket.class, TicketEntity.class);

        return mapperResult;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


}
