package ua.edu.ukma.event_management_system.user.api;

import ua.edu.ukma.event_management_system.user.internal.UserDto;
import ua.edu.ukma.event_management_system.user.internal.User;

import java.util.List;

public interface UserApi {

    List<User> getAllUsers();

    User getUserById(Long userId);

    User createUser(UserDto userDto);

    User updateUser(Long userId, UserDto userDto);

    void deleteUser(Long userId);

    User authenticateUser(String username, String password);
}
