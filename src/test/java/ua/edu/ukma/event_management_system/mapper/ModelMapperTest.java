package ua.edu.ukma.event_management_system.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.edu.ukma.event_management_system.Configurator;
import ua.edu.ukma.event_management_system.domain.Building;
import ua.edu.ukma.event_management_system.domain.Event;
import ua.edu.ukma.event_management_system.domain.User;
import ua.edu.ukma.event_management_system.domain.UserRole;
import ua.edu.ukma.event_management_system.dto.EventDto;
import ua.edu.ukma.event_management_system.dto.UserDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ModelMapperTest {

    @Autowired
    private ModelMapper modelMapper;

    private Building getBuilding1(){
        Building building = new Building();
        building.setId(2);
        building.setAddress("Bandery, 2");
        building.setHourlyRate(150);
        building.setAreaM2(100);
        building.setCapacity(100);
        building.setDescription("This is a test");
        building.setRating(List.of());
        return building;
    }

    private Event getEventDomain1(){
        Event event = new Event();
        event.setId(1);
        event.setEventTitle("Test Event");
        event.setDateTimeStart(LocalDateTime.now());
        event.setDateTimeEnd(LocalDateTime.now().plusDays(1));
        event.setBuilding(getBuilding1());
        event.setDescription("This is a test");
        event.setNumberOfTickets(90);
        event.setMinAgeRestriction(10);
        event.setRating(List.of());
        event.setUsers(List.of());
        return event;
    }

    private EventDto getEventDto1(){
        EventDto eventDto = new EventDto();
        eventDto.setId(1);
        eventDto.setEventTitle("Test Event");
        eventDto.setDateTimeStart(LocalDateTime.now());
        eventDto.setDateTimeEnd(LocalDateTime.now().plusDays(1));
        eventDto.setDescription("This is a test");
        eventDto.setBuilding(2);
        eventDto.setNumberOfTickets(90);
        eventDto.setMinAgeRestriction(10);
        eventDto.setRating(List.of());
        eventDto.setUsers(List.of());
        return eventDto;
    }

    private User getUserDomain(){
        User user = new User();
        user.setId(1);
        user.setUserRole(UserRole.USER);
        user.setUsername("John Doe");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPhoneNumber("+380147852369");
        user.setPassword("password");
        user.setDateOfBirth(LocalDate.of(2004, 11, 2));
        return user;
    }

    private UserDto getUserDto(){
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setUserRole(UserRole.USER);
        userDto.setUsername("John Doe");
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john.doe@example.com");
        userDto.setPhoneNumber("+380147852369");
        userDto.setPassword("password");
        userDto.setDateOfBirth(LocalDate.of(2004, 11, 2));
        return userDto;
    }

    @Test
    void testModelMapper2() {
        assertEquals(getUserDto(), modelMapper.map(getUserDomain(), UserDto.class));
    }

    @Test
    void testModelMapper1() {
        assertEquals(getEventDto1(), modelMapper.map(getEventDomain1(), EventDto.class));
    }

}
