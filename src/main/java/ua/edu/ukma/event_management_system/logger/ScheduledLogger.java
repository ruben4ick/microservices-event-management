package ua.edu.ukma.event_management_system.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.edu.ukma.event_management_system.service.interfaces.TicketService;

import java.time.LocalDateTime;

@Service
public class ScheduledLogger {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledLogger.class);

    private final TicketService ticketService;

    @Autowired
    public ScheduledLogger(TicketService ticketService) {
        this.ticketService = ticketService;
    }


    @Scheduled(cron = "0 50 23 * * *")
    private void logSalesForDay(){
        LocalDateTime now = LocalDateTime.now();
        logger.info("Tickets sold {}.{}.{} --- {}", now.getDayOfMonth(), now.getMonth(), now.getYear(), ticketService.getAllTicketsCreatedToday().size());
    }

}
