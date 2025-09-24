package ua.edu.ukma.event_management_system.building.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buildings")
@RequiredArgsConstructor
public class BuildingController {

    // TODO: Add BuildingManagement service when implemented

    @GetMapping
    public ResponseEntity<List<Building>> getAllBuildings() {
        // TODO: Implement getAllBuildings
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/{buildingId}")
    public ResponseEntity<Building> getBuildingById(@PathVariable Long buildingId) {
        // TODO: Implement getBuildingById
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Building> createBuilding(@RequestBody BuildingDto buildingDto) {
        // TODO: Implement createBuilding
        return ResponseEntity.status(501).build();
    }

    @PutMapping("/{buildingId}")
    public ResponseEntity<Building> updateBuilding(@PathVariable Long buildingId, @RequestBody BuildingDto buildingDto) {
        // TODO: Implement updateBuilding
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{buildingId}")
    public ResponseEntity<Void> deleteBuilding(@PathVariable Long buildingId) {
        // TODO: Implement deleteBuilding
        return ResponseEntity.status(501).build();
    }
}
