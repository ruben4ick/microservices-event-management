package ua.edu.ukma.event_management_system.service.interfaces;

import ua.edu.ukma.event_management_system.domain.Building;
import ua.edu.ukma.event_management_system.domain.BuildingRating;
import ua.edu.ukma.event_management_system.dto.BuildingDto;
import ua.edu.ukma.event_management_system.dto.UserDto;

import java.util.List;

public interface BuildingService {

    Building createBuilding(BuildingDto building);

    List<Building> getAllBuildings();

    Building getBuildingById(Long id);

    void updateBuilding(Long id, BuildingDto updatedBuilding);

    void deleteBuilding(Long id);

    BuildingRating rateBuilding(BuildingDto building, byte rating, UserDto user, String comment);

    List<BuildingRating> getAllByBuildingIdAndRating(long buildingId, byte rating);

    List<Building> getAllByCapacity(int capacity);

    BuildingRating getRatingById(long id);
}
