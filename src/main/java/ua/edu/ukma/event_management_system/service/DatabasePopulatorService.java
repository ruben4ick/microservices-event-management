package ua.edu.ukma.event_management_system.service;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ua.edu.ukma.event_management_system.domain.UserRole;
import ua.edu.ukma.event_management_system.entity.*;
import ua.edu.ukma.event_management_system.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional
public class DatabasePopulatorService {

    private final BuildingRepository buildingRepository;
    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public DatabasePopulatorService(BuildingRepository br, EventRepository er,
                                    TicketRepository tr, UserRepository ur) {
        this.buildingRepository = br;
        this.eventRepository = er;
        this.ticketRepository = tr;
        this.userRepository = ur;
    }

    public void populateDatabase() {
        UserEntity user1 = new UserEntity(UserRole.USER, "and123", "Andriy",
                "Petrenko", "andriisuper@gmail.com",
                "$2a$10$JFbuJfw3FZSs.9rDOO3jiOP2r9HjnJczXsWiqvEhkCyyWVHNjuQyy", // Andri1Sup3r
                "380999777444", LocalDate.of(2005, 01, 01));
        UserEntity user2 = new UserEntity(UserRole.ORGANIZER, "maria_shevchenko", "Maria",
                "Shevchenko", "shevchenkoM@gmail.com",
                "$2a$10$lhATh/lhdonUDlGJrhZtiOU0U8AB18UvdIeTr2mTAMObZZ3G4e2Ui", // maria1234
                "380111486754", LocalDate.of(2010, 01, 01));
        UserEntity admin = new UserEntity(UserRole.ADMIN, "admin", "Admin",
                "eqwe", "emsAdmin@gmail.com",
                "$2a$10$NY5RpFbjoy4TnN.DPH/OEOhnq0.2/sNcfwbJHLW5YTfW.wOugwTsS", // admin123
                "380777777777", LocalDate.of(1995, 01, 01));
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(admin);

        BuildingEntity building1 = new BuildingEntity("123 Main St", 100, 300,
                500, "Conference Hall");
        BuildingEntity building2 = new BuildingEntity("654 Central St", 500, 200,
                100, "Concert Hall");
        buildingRepository.save(building1);
        buildingRepository.save(building2);

        // Create events without images for API-only version
        EventEntity event1 = new EventEntity("queen-concert", LocalDateTime.now().minusDays(10),
                LocalDateTime.now().minusDays(10).plusHours(2),
                building2, "A great queen concert", 100, 18, null, admin, 100);

        EventEntity event2 = new EventEntity("it-conference", LocalDateTime.now().plusHours(3),
                LocalDateTime.now().plusHours(6),
                building1, "IT conference discussing Django vs SpringBoot",
                50, 25, null, user2, 150);

        EventEntity eventZeroTickets = new EventEntity("High school reunion",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(3),
                building2, "zero tickets event",
                0, 1, null, user2, 200);
        eventRepository.save(event1);
        eventRepository.save(event2);
        eventRepository.save(eventZeroTickets);

        TicketEntity ticket1 = new TicketEntity(user1, event1, 100, LocalDateTime.now());
        TicketEntity ticket2 = new TicketEntity(user1, event2, 200, LocalDateTime.now());
        ticketRepository.save(ticket1);
        ticketRepository.save(ticket2);

    }
}