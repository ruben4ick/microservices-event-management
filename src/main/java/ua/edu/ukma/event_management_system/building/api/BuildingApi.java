package ua.edu.ukma.event_management_system.building.api;

import ua.edu.ukma.event_management_system.building.internal.BuildingDto;
import ua.edu.ukma.event_management_system.building.internal.Building;

import java.util.List;

public interface BuildingApi {

    List<Building> getAllBuildings();

    Building getBuildingById(Long buildingId);

    Building createBuilding(BuildingDto buildingDto);

    Building updateBuilding(Long buildingId, BuildingDto buildingDto);

    void deleteBuilding(Long buildingId);
}
