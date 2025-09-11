package ua.edu.ukma.event_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ukma.event_management_system.entity.EventEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
    Optional<EventEntity> findAllByEventTitle(String title);

    @Query("SELECT e.eventTitle, e.dateTimeStart FROM EventEntity e")
    List<Object[]> findEventNameAndDates();

    List<EventEntity> findEventEntitiesByDateTimeEndAfter(LocalDateTime dateTime);
    List<EventEntity> findEventEntitiesByCreator_Id(Long id);
    List<EventEntity> findEventEntitiesByBuildingIdAndDateTimeEndAfterAndDateTimeStartBefore(long id, LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd);
}