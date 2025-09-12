/**
 * User module - manages user authentication, authorization, and user data.
 * 
 * This module provides:
 * - User registration and authentication
 * - JWT token management
 * - User profile management
 * - Role-based access control
 */
@org.springframework.modulith.ApplicationModule(
    displayName = "User Management",
    allowedDependencies = {"shared"}
)
package ua.edu.ukma.event_management_system.user;
