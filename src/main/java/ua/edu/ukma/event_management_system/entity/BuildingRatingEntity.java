package ua.edu.ukma.event_management_system.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "building_rating")
public class BuildingRatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "building_id", nullable = false)
    private BuildingEntity building;

    @Column(nullable = false)
    private byte rating;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    private UserEntity author;

    @Column(nullable = false)
    private String comment;

    public BuildingRatingEntity(BuildingEntity building, byte rating, UserEntity author, String comment) {
        this.building = building;
        this.rating = rating;
        this.author = author;
        this.comment = comment;
    }

    public BuildingRatingEntity() {}

    @Override
    public String toString() {
        return "BuildingRatingEntity{"
                +
                "id: " + id +
                ", building :{ id: " + getBuilding().getId() + ", ratingSize: " + getBuilding().getRating().size() + "}, " +
                ", rating: " + rating +
                ", author: " + author +
                ", comment: " + comment +
                "}";
    }
}