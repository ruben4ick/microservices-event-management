package ua.edu.ukma.event_management_system.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "building")
@ToString
public class BuildingEntity {
    @Id
    // default strategy, JPA automatically selects the appropriate
    // generation strategy based on the database used
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Setter
    @Column(nullable = false)
    private String address;

    @Setter
    @Column(nullable = false)
    private int hourlyRate;

    @Setter
    @Column(nullable = false)
    private int areaM2;

    @Setter
    @Column(nullable = false)
    private int capacity;

    @Setter
    @Column(length = 500)
    private String description;

    @Setter
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "building")
    private List<BuildingRatingEntity> rating;

    public BuildingEntity(String address, int hourlyRate, int areaM2, int capacity, String description) {
        this.address = address;
        this.hourlyRate = hourlyRate;
        this.areaM2 = areaM2;
        this.capacity = capacity;
        this.description = description;
    }
}
