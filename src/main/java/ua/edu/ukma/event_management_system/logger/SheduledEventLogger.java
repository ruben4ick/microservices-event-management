package ua.edu.ukma.event_management_system.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.edu.ukma.event_management_system.repository.EventRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SheduledEventLogger {

    private static final Logger logger = LoggerFactory.getLogger(SheduledEventLogger.class);

    private final EventRepository eventRepository;

    @Autowired
    public SheduledEventLogger(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    private void logEventsForToday(){
        try {
            List<Object[]> events = eventRepository.findEventNameAndDates();
            String xs = events.stream()
                    .filter(event -> ((LocalDateTime) event[1]).toLocalDate().equals(LocalDate.now()))
                    .map(event -> (String) event[0])
                    .collect(Collectors.joining(", "));
            logger.info("Events planned for today: {}", xs);
        } catch (Exception e) {
            logger.error("Failed to log events: {}", e.getMessage());
        }
    }
}
