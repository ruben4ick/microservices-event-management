/**
 * Ticket module - manages ticket sales and reservations.
 * 
 * This module provides:
 * - Ticket purchase and reservation
 * - Ticket validation
 * - Ticket management for events
 */
@org.springframework.modulith.ApplicationModule(
    displayName = "Ticket Management",
    allowedDependencies = {"shared", "event", "user"}
)
package ua.edu.ukma.event_management_system.ticket;
