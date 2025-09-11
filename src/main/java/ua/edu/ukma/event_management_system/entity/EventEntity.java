package ua.edu.ukma.event_management_system.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "event")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String eventTitle;

    @Column(nullable = false)
    private LocalDateTime dateTimeStart;

    @Column(nullable = false)
    private LocalDateTime dateTimeEnd;

    @ManyToOne
    @JoinColumn(name = "building_id", nullable = false)
    private BuildingEntity building;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private int numberOfTickets;

    @Column(nullable = false)
    private int minAgeRestriction;

    @Lob
    @Column(name = "event_image")
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity creator;

    @Column(nullable = false)
    private double price;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="ticket",
    joinColumns = @JoinColumn(name = "event_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<UserEntity> users;

    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER)
    private List<EventRatingEntity> rating;

    public EventEntity(String eventTitle, LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd,
                       BuildingEntity building, String description, int numberOfTickets, int minAgeRestriction, byte[] image,
                       UserEntity creator, double price) {
        this.eventTitle = eventTitle;
        this.dateTimeStart = dateTimeStart;
        this.dateTimeEnd = dateTimeEnd;
        this.building = building;
        this.description = description;
        this.numberOfTickets = numberOfTickets;
        this.minAgeRestriction = minAgeRestriction;
        this.image = image;
        this.creator = creator;
        this.price = price;
    }
}