package ua.edu.ukma.event_management_system.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.edu.ukma.event_management_system.exceptions.EventFullException;
import ua.edu.ukma.event_management_system.exceptions.IllegalNameException;

import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EventFullException.class)
    public String handleEventFullException(EventFullException e, Model model) {
        model.addAttribute("errors", List.of("The event is already full!"));
        return "error";
    }

    @ExceptionHandler(IllegalNameException.class)
    public ResponseEntity<String> handleIllegalNameException(IllegalNameException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
