package ua.edu.ukma.event_management_system.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Building {
    private int id;
    private String address;
    private int hourlyRate;
    private int areaM2;
    private int capacity; // max number of people that the building can host
    private String description;
    private List<BuildingRating> rating;

    public Building(int id, String address, int hourlyRate, int areaM2, int capacity,
                    String description) {
        this.id = id;
        this.address = address;
        this.hourlyRate = hourlyRate;
        this.areaM2 = areaM2;
        this.capacity = capacity;
        this.description = description;
        this.rating = new ArrayList<>();
    }

    public Building(int id, String address, int hourlyRate, int areaM2, int capacity, String description, List<BuildingRating> rating) {
        this(id, address, hourlyRate, areaM2, capacity, description);
        this.rating = rating;
    }
}
