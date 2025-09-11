package ua.edu.ukma.event_management_system.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import ua.edu.ukma.event_management_system.domain.User;

@Data
@NoArgsConstructor
public class BuildingRatingDto {
	private long id;
	@Range(min = 0, max = 10, message = "Rating must be in range [1, 10].")
	private byte rating;
	private User author;
	private String comment;
}
