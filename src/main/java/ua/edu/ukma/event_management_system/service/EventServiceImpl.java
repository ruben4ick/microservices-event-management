package ua.edu.ukma.event_management_system.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ua.edu.ukma.event_management_system.domain.Event;
import ua.edu.ukma.event_management_system.dto.EventDto;
import ua.edu.ukma.event_management_system.entity.BuildingEntity;
import ua.edu.ukma.event_management_system.entity.EventEntity;
import ua.edu.ukma.event_management_system.exceptions.handler.ContentValidator;
import ua.edu.ukma.event_management_system.repository.BuildingRepository;
import ua.edu.ukma.event_management_system.repository.EventRepository;
import ua.edu.ukma.event_management_system.service.interfaces.EventService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private ModelMapper modelMapper;
    private EventRepository eventRepository;
    private BuildingRepository buildingRepository;

    @Autowired
    public void setModelMapper(@Lazy ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setEventRepository(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Autowired
    public void setBuildingRepository(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    @Override
    public List<Event> getAllEvents(){
        return eventRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public Event getEventById(long eventId) {
        return toDomain(eventRepository.findById(eventId).orElseThrow());
    }

    @Override
    public List<Event> getAllEventsByTitle(String title) {
        return eventRepository.findAllByEventTitle(title).stream().map(this::toDomain).toList();
    }

    @Override
    public void createEvent(EventDto event) {
        // Ensures the building is saved before saving the event
        EventEntity toSave = toEntity(event);
        BuildingEntity buildingEntity = toSave.getBuilding();
        if (buildingEntity != null) {
            Optional<BuildingEntity> existingBuilding = buildingRepository.findById(buildingEntity.getId());
            if (existingBuilding.isEmpty())
                buildingEntity = buildingRepository.save(buildingEntity);
            else
                buildingEntity = existingBuilding.get();
        }
        toSave.setBuilding(buildingEntity);
        toDomain(eventRepository.save(toSave));
    }

    @Override
    public void updateEvent(Long id, EventDto updatedEvent) {
        Optional<EventEntity> existingEventOpt = eventRepository.findById(id);

        if (existingEventOpt.isPresent()) {
            EventEntity existingEvent = existingEventOpt.get();
            ContentValidator.validateContent(updatedEvent.getEventTitle());
            existingEvent.setEventTitle(updatedEvent.getEventTitle());
            existingEvent.setDateTimeStart(updatedEvent.getDateTimeStart());
            existingEvent.setDateTimeEnd(updatedEvent.getDateTimeEnd());
            existingEvent.setImage(updatedEvent.getImage());
            existingEvent.setDescription(updatedEvent.getDescription());
            existingEvent.setNumberOfTickets(updatedEvent.getNumberOfTickets());
            existingEvent.setMinAgeRestriction(updatedEvent.getMinAgeRestriction());
            existingEvent.setPrice(updatedEvent.getPrice());


            Optional<BuildingEntity> build = buildingRepository.findById(updatedEvent.getBuilding());
            if (build.isEmpty()) {return;}

            BuildingEntity buildingEntity = build.get();
            if(buildingEntity != null){
                Optional<BuildingEntity> existingBuilding = buildingRepository.findById(buildingEntity.getId());
                if (!existingBuilding.isPresent()){
                    buildingEntity = buildingRepository.save(buildingEntity);
                }else{
                    buildingEntity = existingBuilding.get();
                }
            }

            existingEvent.setBuilding(buildingEntity);
            existingEvent.setDescription(updatedEvent.getDescription());
            existingEvent.setNumberOfTickets(updatedEvent.getNumberOfTickets());
            existingEvent.setMinAgeRestriction(updatedEvent.getMinAgeRestriction());
            eventRepository.save(existingEvent);
        }
    }

    @Override
    public void deleteEvent(long eventId) {
        eventRepository.deleteById(eventId);
    }

    @Override
    public List<Event> getAllRelevant() {
        return eventRepository.findEventEntitiesByDateTimeEndAfter(LocalDateTime.now()).stream().map(this::toDomain).toList();
    }

    @Override
    public List<Event> getAllForOrganizer(Long organizerId) {
        return eventRepository.findEventEntitiesByCreator_Id(organizerId).stream().map(this::toDomain).toList();
    }

    @Override
    public List<Event> getAllThatIntersect(LocalDateTime start, LocalDateTime end, long id) {
        return eventRepository.findEventEntitiesByBuildingIdAndDateTimeEndAfterAndDateTimeStartBefore(id, start, end).stream().map(this::toDomain).toList();
    }

    private Event toDomain(EventEntity event){
        return modelMapper.map(event, Event.class);
    }

    private EventEntity toEntity(EventDto event){
        return modelMapper.map(event, EventEntity.class);
    }

}
