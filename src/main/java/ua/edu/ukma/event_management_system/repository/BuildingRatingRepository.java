package ua.edu.ukma.event_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ukma.event_management_system.entity.BuildingRatingEntity;

import java.util.List;

public interface BuildingRatingRepository extends JpaRepository<BuildingRatingEntity, Long> {
    List<BuildingRatingEntity> findAllByBuildingIdAndRating(long buildingId, byte rating);
}
