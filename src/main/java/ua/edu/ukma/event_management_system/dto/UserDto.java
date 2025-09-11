package ua.edu.ukma.event_management_system.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.edu.ukma.event_management_system.domain.UserRole;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class UserDto {
    private long id;
    private UserRole userRole;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$")
    private String phoneNumber;
    private LocalDate dateOfBirth;

    public UserDto(int id, UserRole userRole, String username, String firstName, String lastName, String email, String phoneNumber, LocalDate dateOfBirth) {
        this.id = id;
        this.userRole = userRole;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    public UserDto(int id, UserRole userRole, String username, String firstName, String lastName, String email, String password, String phoneNumber, LocalDate dateOfBirth) {
        this.id = id;
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
