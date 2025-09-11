package ua.edu.ukma.event_management_system.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BuildingRating {
	private long id;
	private long building;
	private byte rating;
	private User author;
	private String comment;
}
