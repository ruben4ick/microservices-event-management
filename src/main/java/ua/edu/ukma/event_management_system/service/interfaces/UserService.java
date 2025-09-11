package ua.edu.ukma.event_management_system.service.interfaces;

import ua.edu.ukma.event_management_system.domain.User;
import ua.edu.ukma.event_management_system.domain.UserRole;
import ua.edu.ukma.event_management_system.dto.UserDto;

import java.util.List;

public interface UserService {

    User createUser(UserDto user);

    List<User> getAllUsers();

    User getUserById(long userId);

    User getUserByUsername(String username);

    void updateUser(long id, UserDto updatedUser);

    boolean removeUser(long userId);

    boolean checkPermission(User user, UserRole role);

    String verify(String username, String password);
}
