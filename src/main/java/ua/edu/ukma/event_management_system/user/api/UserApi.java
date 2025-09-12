package ua.edu.ukma.event_management_system.user.api;

import ua.edu.ukma.event_management_system.shared.api.UserDto;
import ua.edu.ukma.event_management_system.user.internal.User;

import java.util.List;

/**
 * Public API for User module.
 * This interface defines the external contract for user operations.
 */
public interface UserApi {
    
    /**
     * Get all users.
     * @return list of all users
     */
    List<User> getAllUsers();
    
    /**
     * Get user by ID.
     * @param userId the user ID
     * @return the user
     */
    User getUserById(Long userId);
    
    /**
     * Create a new user.
     * @param userDto the user data
     * @return the created user
     */
    User createUser(UserDto userDto);
    
    /**
     * Update an existing user.
     * @param userId the user ID
     * @param userDto the updated user data
     * @return the updated user
     */
    User updateUser(Long userId, UserDto userDto);
    
    /**
     * Delete a user.
     * @param userId the user ID
     */
    void deleteUser(Long userId);
    
    /**
     * Authenticate a user.
     * @param username the username
     * @param password the password
     * @return the authenticated user or null if authentication fails
     */
    User authenticateUser(String username, String password);
}
