package ua.edu.ukma.event_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ukma.event_management_system.entity.EventRatingEntity;

public interface EventRatingRepository extends JpaRepository<EventRatingEntity, Long> {
}
