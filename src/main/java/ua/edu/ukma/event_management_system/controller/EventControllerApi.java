package ua.edu.ukma.event_management_system.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ua.edu.ukma.event_management_system.dto.EventDto;
import ua.edu.ukma.event_management_system.service.interfaces.EventService;
import ua.edu.ukma.event_management_system.domain.Event;

import java.util.*;

@RestController
@RequestMapping("api/event")
@ConditionalOnExpression("${api.event.enable}")
public class EventControllerApi {

    private ModelMapper modelMapper;
    private EventService eventService;

    @Value("${outer.api.call.url}")
    private String outerApiCall;

    @Autowired
    public void setModelWrapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setEventService(EventService eventService){
        this.eventService = eventService;
    }

    @GetMapping("/{id}")
    public EventDto getEvent(@PathVariable long id){
        return toDto(eventService.getEventById(id));
    }

    @GetMapping("/")
    public List<EventDto> getEvents(@RequestParam(required = false) String title){
        if(title == null) {
            return eventService.getAllEvents().stream().map(this::toDto).toList();
        }else {
            return eventService.getAllEventsByTitle(title).stream().map(this::toDto).toList();
        }
    }

    @PostMapping("/")
    public void createNewEvent(@RequestBody EventDto eventDto){
        if (eventDto.getDescription().isEmpty()){
            ResponseEntity<String> response = new RestTemplate()
                    .getForEntity(outerApiCall, String.class);
            eventDto.setDescription(response.getBody());
        }
        eventService.createEvent(eventDto);
    }

    @PutMapping("/{id}")
    public void updateEvent(@PathVariable long id, @RequestBody EventDto eventDto){
        eventService.updateEvent(id, eventDto);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable long id) {
        eventService.deleteEvent(id);
    }

    private EventDto toDto(Event event){
        return modelMapper.map(event, EventDto.class);
    }
}