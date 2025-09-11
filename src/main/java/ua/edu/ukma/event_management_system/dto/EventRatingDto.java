package ua.edu.ukma.event_management_system.dto;


import lombok.*;
import org.hibernate.validator.constraints.Range;


@Data
@NoArgsConstructor
public class EventRatingDto {
    private long id;
    private int event;
    @Range(min = 0, max = 10, message = "Rating must be in range [1, 10].")
    private byte rating;
    private UserDto author;
    private String comment;
}