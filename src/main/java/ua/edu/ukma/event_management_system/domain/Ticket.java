package ua.edu.ukma.event_management_system.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class Ticket {
    private long id;
    private User user;
    private Event event;
    private double price;
    private LocalDateTime purchaseDate;

}
