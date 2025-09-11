package ua.edu.ukma.event_management_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "event_rating")
public class EventRatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;

    @Column(nullable = false)
    private byte rating;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private UserEntity author;

    @Column(nullable = false)
    private String comment;

    public EventRatingEntity() {
        //No args constructor
    }
}
