package ua.edu.ukma.event_management_system.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
public class User {
    private long id;
    private UserRole userRole;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private LocalDate dateOfBirth;

    public long getAge() {
        LocalDate now = LocalDate.now();
        return ChronoUnit.YEARS.between(dateOfBirth, now);
    }

    public User(long id, UserRole userRole, String username, String firstName, String lastName, String email, String phoneNumber, String password, LocalDate dateOfBirth) {
        this.id = id;
        this.userRole = userRole;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }
}
