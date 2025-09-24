package ua.edu.ukma.event_management_system.user.internal;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User createUser(UserDto userDto) {
        User user = new User();
        user.setId(System.currentTimeMillis());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUserRole(userDto.getUserRole());
        user.setDateOfBirth(userDto.getDateOfBirth());
        
        return user;
    }
}
