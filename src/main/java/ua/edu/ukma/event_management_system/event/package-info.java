/**
 * Event module - manages events and event-related operations.
 * 
 * This module provides:
 * - Event CRUD operations
 * - Event scheduling and time management
 * - Event rating system
 * - Event search and filtering
 */
@org.springframework.modulith.ApplicationModule(
    displayName = "Event Management",
    allowedDependencies = {"shared", "building", "user"}
)
package ua.edu.ukma.event_management_system.event;
