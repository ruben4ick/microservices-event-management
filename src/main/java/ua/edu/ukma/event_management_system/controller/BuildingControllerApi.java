package ua.edu.ukma.event_management_system.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import ua.edu.ukma.event_management_system.domain.Building;
import ua.edu.ukma.event_management_system.domain.BuildingRating;
import ua.edu.ukma.event_management_system.dto.BuildingDto;
import ua.edu.ukma.event_management_system.dto.BuildingRatingDto;
import ua.edu.ukma.event_management_system.service.interfaces.BuildingService;
import jakarta.validation.Valid;

import java.util.*;

@RestController
@RequestMapping("api/building")
@ConditionalOnExpression("${api.building.enable}")
public class BuildingControllerApi {

	private ModelMapper modelMapper;
	private BuildingService buildingService;

	@Autowired
	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Autowired
	public void setBuildingService(BuildingService buildingService) {
		this.buildingService = buildingService;
	}

	@GetMapping("/{id}")
	public BuildingDto getBuilding(@PathVariable long id) {
		return toDto(buildingService.getBuildingById(id));
	}

	@GetMapping("/")
	public List<BuildingDto> getBuildings(@RequestParam(required = false) Integer capacity) {
		if (capacity == null) {
			return buildingService.getAllBuildings()
					.stream()
					.map(this::toDto)
					.toList();
		} else {
			return buildingService.getAllByCapacity(capacity)
					.stream()
					.map(this::toDto)
					.toList();
		}
	}

	@PostMapping
	public ResponseEntity<?> createNewBuilding(@ModelAttribute("buildingDto") @Valid BuildingDto buildingDto,
											   BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()){
			Map<String, String> errors = new HashMap<>();
			bindingResult.getFieldErrors().forEach(error ->
				errors.put(error.getField(), error.getDefaultMessage())
			);
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}

		if(buildingDto.getDescription() == null || buildingDto.getDescription().isEmpty()){
			RestClient client = RestClient.create();
			String defaultDescription = client.get()
					.uri("https://baconipsum.com/api/?type=meat-and-filler&sentences=2&format=text")
					.retrieve()
					.body(String.class);
			buildingDto.setDescription(defaultDescription);
		}

		Building returned = buildingService.createBuilding(buildingDto);
		return new ResponseEntity<>(returned, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateBuilding(@PathVariable long id, @RequestBody @Valid BuildingDto buildingDto,
							   BindingResult bindingResult) {
		if(bindingResult.hasErrors()){
			Map<String, String> errors = new HashMap<>();
			bindingResult.getFieldErrors().forEach(error ->
				errors.put(error.getField(), error.getDefaultMessage())
			);
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}
		buildingService.updateBuilding(id, buildingDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBuilding(@PathVariable long id) {
		buildingService.deleteBuilding(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/{buildingId}/{rating}")
	public List<BuildingRatingDto> getRated(@PathVariable long buildingId, @PathVariable byte rating) {
		return buildingService.getAllByBuildingIdAndRating(buildingId, rating)
				.stream()
				.map(this::toDto)
				.toList();
	}

	private BuildingDto toDto(Building building) {
		return modelMapper.map(building, BuildingDto.class);
	}

	private BuildingRatingDto toDto(BuildingRating buildingRating) {
		return modelMapper.map(buildingRating, BuildingRatingDto.class);
	}
}
