package ua.edu.ukma.event_management_system.building.internal;

import lombok.Data;
import lombok.NoArgsConstructor;
import ua.edu.ukma.event_management_system.user.internal.User;

@Data
@NoArgsConstructor
public class BuildingRating {
	private long id;
	private long building;
	private byte rating;
	private User author;
	private String comment;
}
