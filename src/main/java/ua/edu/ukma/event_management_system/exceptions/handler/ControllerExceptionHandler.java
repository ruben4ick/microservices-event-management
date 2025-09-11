package ua.edu.ukma.event_management_system.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String exceptionHandler(NoSuchElementException e, Model model) {
		model.addAttribute("error", e.getLocalizedMessage());
		return "error";
	}
}
