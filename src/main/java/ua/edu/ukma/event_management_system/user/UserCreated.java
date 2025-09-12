package ua.edu.ukma.event_management_system.user;

import org.jmolecules.event.types.DomainEvent;

/**
 * Domain event published when a user is created.
 */
public record UserCreated(Long userId, String username, String email) implements DomainEvent {}
