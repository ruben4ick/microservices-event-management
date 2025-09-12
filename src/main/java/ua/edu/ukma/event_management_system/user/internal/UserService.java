package ua.edu.ukma.event_management_system.user.internal;

import org.springframework.stereotype.Service;
import ua.edu.ukma.event_management_system.shared.api.UserDto;

/**
 * Simple user service for the User module.
 */
@Service
public class UserService {

    public User createUser(UserDto userDto) {
        // Simple implementation - just create a user object
        User user = new User();
        user.setId(System.currentTimeMillis()); // Simple ID generation
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUserRole(userDto.getUserRole());
        user.setDateOfBirth(userDto.getDateOfBirth());
        
        return user;
    }
}
