package ua.edu.ukma.event_management_system.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket")
public class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private LocalDateTime purchaseDate;

    public TicketEntity(UserEntity user, EventEntity event, int price, LocalDateTime purchaseDate) {
        this.user = user;
        this.event = event;
        this.price = price;
        this.purchaseDate = purchaseDate;
    }
}