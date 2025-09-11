package ua.edu.ukma.event_management_system.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.edu.ukma.event_management_system.domain.User;
import ua.edu.ukma.event_management_system.dto.UserDto;
import ua.edu.ukma.event_management_system.service.interfaces.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.*;

@RestController
@RequestMapping("api/user")
@ConditionalOnExpression("${api.user.enable}")
public class UserControllerApi {

    private static final Logger logger = LoggerFactory.getLogger(UserControllerApi.class);
    private static final Marker USER_ACTION_MARKER = MarkerFactory.getMarker("USER_ACTION");
    private static final Marker VALIDATION_MARKER = MarkerFactory.getMarker("VALIDATION");
    private static final Marker USER_ERROR_MARKER = MarkerFactory.getMarker("USER_ERROR");

    private ModelMapper modelMapper;
    private UserService userService;

    private static final String USER_ID = "userID";

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setBuildingService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable long id) {
        try{
            MDC.put(USER_ID, String.valueOf(id));
            return toDto(userService.getUserById(id));
        }finally{
            MDC.clear();
        }
    }

    @GetMapping
    public List<UserDto> getUsers() {
        logger.info(USER_ACTION_MARKER, "Got all user");
        return userService.getAllUsers()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createNewUser(@RequestBody @Valid UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
            );
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        userService.createUser(userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody @Valid UserDto userDto, BindingResult bindingResult) {
        try {
            MDC.put(USER_ID, String.valueOf(id));
            if (bindingResult.hasErrors()) {
                Map<String, String> errors = new HashMap<>();
                bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
                );
                logger.error(VALIDATION_MARKER, "Validation error occured: {}", errors);
                return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
            }
            logger.info(USER_ACTION_MARKER, "Put user");
            userService.updateUser(id, userDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }finally{
            MDC.clear();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        try {
            MDC.put(USER_ID, String.valueOf(id));
            boolean userRemoved = userService.removeUser(id);
            if(!userRemoved) {
                logger.warn(USER_ERROR_MARKER, "User deletion failed. User {} not found.", id);
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
            logger.info(USER_ACTION_MARKER, "Deleted user");
            return new ResponseEntity<>(HttpStatus.OK);
        }finally {
            MDC.clear();
        }
    }

    private UserDto toDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
