package ua.edu.ukma.event_management_system.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventRating {
    private long id;
    private Event event;
    private byte rating;
    private User author;
    private String comment;
}
