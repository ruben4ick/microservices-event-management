package ua.edu.ukma.event_management_system.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ua.edu.ukma.event_management_system.domain.Building;
import ua.edu.ukma.event_management_system.dto.BuildingDto;
import ua.edu.ukma.event_management_system.service.interfaces.BuildingService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class BuildingServiceTest {

    @Autowired
    private BuildingService buildingService;

    @Order(1)
    @Test
    void testGetAllBuildings() {
        List<Building> buildings = buildingService.getAllBuildings();
        assertEquals(2, buildings.size(), "Expected two buildings in the database.");
    }

    @Order(2)
    @Test
    void testGetBuildingById() {
        Building building = buildingService.getBuildingById(1L); // Assuming ID 1 exists
        assertNotNull(building, "Building with ID 1 should exist.");
        assertEquals("123 Main St", building.getAddress(), "Building address should match.");
    }

    @Order(3)
    @Test
    void testCreateBuilding() {
        BuildingDto newBuilding = new BuildingDto();
        newBuilding.setAddress("New Conference Hall");
        newBuilding.setHourlyRate(100);
        newBuilding.setAreaM2(200);
        newBuilding.setCapacity(150);
        newBuilding.setDescription("A spacious conference hall");

        buildingService.createBuilding(newBuilding);

        List<Building> buildings = buildingService.getAllBuildings();
        assertEquals(3, buildings.size(), "Expected one more building after creation.");
        assertTrue(buildings.stream().anyMatch(b -> b.getAddress().equals("New Conference Hall")),
                "The new building should be present in the database.");
    }

    @Order(4)
    @Test
    void testUpdateBuilding() {
        BuildingDto updatedBuilding = new BuildingDto();
        updatedBuilding.setAddress("Updated Address");
        updatedBuilding.setHourlyRate(120);
        updatedBuilding.setAreaM2(250);
        updatedBuilding.setCapacity(180);
        updatedBuilding.setDescription("Updated description");

        buildingService.updateBuilding(1L, updatedBuilding);

        Building building = buildingService.getBuildingById(1L);
        assertEquals("Updated Address", building.getAddress(), "Building address should be updated.");
        assertEquals(180, building.getCapacity(), "Capacity should be updated.");
    }

    @Order(5)
    @Test
    void testDeleteBuilding() {
        buildingService.deleteBuilding(3L);

        List<Building> buildings = buildingService.getAllBuildings();
        assertEquals(2, buildings.size(), "Expected one less building after deletion.");
        assertFalse(buildings.stream().anyMatch(b -> b.getId() == 3L),
                "Building with ID 3 should no longer exist.");
    }
}
