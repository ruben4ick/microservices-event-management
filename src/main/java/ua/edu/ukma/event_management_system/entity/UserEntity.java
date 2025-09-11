package ua.edu.ukma.event_management_system.entity;

import jakarta.persistence.*;
import ua.edu.ukma.event_management_system.domain.UserRole;

import java.time.LocalDate;
import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users") // user is reserved keyword
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @ManyToMany(mappedBy = "users")
    private List<EventEntity> events;

    public UserEntity(UserRole userRole, String username, String firstName, String lastName, String email, String password, String phoneNumber, LocalDate dateOfBirth) {
        this.userRole = userRole;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }
}