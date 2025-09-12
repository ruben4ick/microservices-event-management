/**
 * Building module - manages building information and ratings.
 * 
 * This module provides:
 * - Building CRUD operations
 * - Building rating system
 * - Building search and filtering
 */
@org.springframework.modulith.ApplicationModule(
    displayName = "Building Management",
    allowedDependencies = {"shared"}
)
package ua.edu.ukma.event_management_system.building;
