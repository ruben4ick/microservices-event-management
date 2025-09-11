package ua.edu.ukma.event_management_system.controller;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.edu.ukma.event_management_system.config.SecurityConfiguration;
import ua.edu.ukma.event_management_system.domain.Building;
import ua.edu.ukma.event_management_system.dto.BuildingDto;
import ua.edu.ukma.event_management_system.exceptions.handler.ControllerExceptionHandler;
import ua.edu.ukma.event_management_system.service.interfaces.BuildingService;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(excludeAutoConfiguration = SecurityConfiguration.class)
@ContextConfiguration(classes = {BuildingControllerApi.class, ControllerExceptionHandler.class})
@AutoConfigureMockMvc(addFilters = false)
class BuildingControllerApiTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ModelMapper modelMapper;

	@MockBean
	private BuildingService buildingService;

	@Test
	void testGetBuilding() throws Exception {
		Building building1 = building1();
		when(buildingService.getBuildingById(1L)).thenReturn(building1);
		when(modelMapper.map(building1, BuildingDto.class)).thenReturn(building1Dto());

		mockMvc.perform(MockMvcRequestBuilders.get("/api/building/1"))
				.andExpect(status().isOk());
	}

	@Test
	void testGetBuildingWithNonExistingId() throws Exception {
		when(buildingService.getBuildingById(any()))
				.thenThrow(new NoSuchElementException("Building not found: 666"));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/building/666"))
				.andExpect(status().isNotFound());
	}

	@Test
	void testGetBuildings() throws Exception {
		Building building1 = building1();
		Building building2 = building2();
		List<Building> serviceResponse = List.of(building1, building2);
		when(buildingService.getAllBuildings()).thenReturn(serviceResponse);
		when(modelMapper.map(building1, BuildingDto.class)).thenReturn(building1Dto());
		when(modelMapper.map(building2, BuildingDto.class)).thenReturn(building2Dto());

		mockMvc.perform(MockMvcRequestBuilders.get("/api/building/"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$.length()").value(2));
	}

	@Test
	void testGetBuildingsWithCapacity() throws Exception {
		Building building1 = building1();
		when(buildingService.getAllByCapacity(50)).thenReturn(List.of(building1));
		when(modelMapper.map(building1, BuildingDto.class)).thenReturn(building1Dto());

		mockMvc.perform(MockMvcRequestBuilders.get("/api/building/?capacity=50"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$.length()").value(1));
	}

	@Test
	void testGetBuildingsWithCapacityEmpty() throws Exception {
		when(buildingService.getAllByCapacity(any(Integer.class))).thenReturn(Collections.emptyList());

		mockMvc.perform(MockMvcRequestBuilders.get("/api/building/?capacity=10"))
				.andExpect(status().isOk());
	}



	private Building building1() {
		return new Building(
				1,
				"Some street, 1",
				500,
				400,
				50,
				"A building located on Some street, 1"
		);
	}

	private BuildingDto building1Dto() {
		return new BuildingDto(1,
				"Some street, 1",
				500,
				400,
				50,
				"A building located on Some street, 1"
		);
	}

	private Building building2() {
		return new Building(
				2,
				"Another street, 2",
				100,
				120,
				10,
				"A building located on Another street, 2"
		);
	}

	private BuildingDto building2Dto() {
		return new BuildingDto(1,
				"Another street, 2",
				100,
				120,
				10,
				"A building located on Another street, 2"
		);
	}
}
