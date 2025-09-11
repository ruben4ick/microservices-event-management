package ua.edu.ukma.event_management_system.aop;

import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ua.edu.ukma.event_management_system.dto.TicketDto;
import ua.edu.ukma.event_management_system.exceptions.EventFullException;

@Aspect
@Component
public class ExceptionAspect {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAspect.class);

    @Pointcut(value = "execution(* ua.edu.ukma.event_management_system.controller.TicketControllerApi.createTicket(..))")
    private void createTicket() {}

    @AfterThrowing(
            pointcut = "createTicket() && args(ticket)",
            throwing = "exception",
            argNames = "exception, ticket")
    public void catchFullEvent(EventFullException exception, TicketDto ticket) {
        logger.error("Could not create ticket for User(id={}) and Event(id={}) because {}",
                ticket.getUser(),
                ticket.getEvent(),
                exception.getMessage());
    }
}
