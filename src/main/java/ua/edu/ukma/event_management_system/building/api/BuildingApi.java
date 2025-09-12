package ua.edu.ukma.event_management_system.building.api;

import ua.edu.ukma.event_management_system.shared.api.BuildingDto;
import ua.edu.ukma.event_management_system.building.internal.Building;

import java.util.List;

/**
 * Public API for Building module.
 * This interface defines the external contract for building operations.
 */
public interface BuildingApi {
    
    /**
     * Get all buildings.
     * @return list of all buildings
     */
    List<Building> getAllBuildings();
    
    /**
     * Get building by ID.
     * @param buildingId the building ID
     * @return the building
     */
    Building getBuildingById(Long buildingId);
    
    /**
     * Create a new building.
     * @param buildingDto the building data
     * @return the created building
     */
    Building createBuilding(BuildingDto buildingDto);
    
    /**
     * Update an existing building.
     * @param buildingId the building ID
     * @param buildingDto the updated building data
     * @return the updated building
     */
    Building updateBuilding(Long buildingId, BuildingDto buildingDto);
    
    /**
     * Delete a building.
     * @param buildingId the building ID
     */
    void deleteBuilding(Long buildingId);
    
    /**
     * Search buildings by address.
     * @param address the address to search for
     * @return list of matching buildings
     */
    List<Building> searchBuildingsByAddress(String address);
}
