package ua.edu.ukma.event_management_system.service;

import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ua.edu.ukma.event_management_system.domain.Building;
import ua.edu.ukma.event_management_system.domain.BuildingRating;
import ua.edu.ukma.event_management_system.dto.BuildingDto;
import ua.edu.ukma.event_management_system.dto.UserDto;
import ua.edu.ukma.event_management_system.entity.BuildingEntity;
import ua.edu.ukma.event_management_system.entity.BuildingRatingEntity;
import ua.edu.ukma.event_management_system.entity.UserEntity;
import ua.edu.ukma.event_management_system.repository.BuildingRatingRepository;
import ua.edu.ukma.event_management_system.repository.BuildingRepository;
import ua.edu.ukma.event_management_system.service.interfaces.BuildingService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BuildingServiceImpl implements BuildingService {

    private ModelMapper modelMapper;
    private BuildingRepository buildingRepository;
    private BuildingRatingRepository buildingRatingRepository;

    private static final Logger logger = LoggerFactory.getLogger(BuildingServiceImpl.class);


    @Autowired
    void setBuildingRepository(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    @Autowired
    void setBuildingRatingRepository(BuildingRatingRepository buildingRatingRepository) {
        this.buildingRatingRepository = buildingRatingRepository;
    }

    @Autowired
    void setModelMapper(@Lazy ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Building createBuilding(BuildingDto building) {
        BuildingEntity toSave = dtoToEntity(building);
        return toDomain(buildingRepository.save(toSave));
    }

    @Override
//    @RateLimit(maxRequests = 3)
//    @Cacheable(cacheNames="buildings")
    public List<Building> getAllBuildings() {
        return buildingRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
//    @Cacheable(cacheNames="building", key = "#id")
    public Building getBuildingById(Long id) {
        logger.info("Returned building with id: {}", id);
		return toDomain(buildingRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Building not found: " + id)));
    }

    @Override
//    @CachePut(cacheNames = "building", key = "#id")
    public void updateBuilding(Long id, BuildingDto updatedBuilding) {
        Optional<BuildingEntity> existingBuilding = buildingRepository.findById(id);
        if (existingBuilding.isPresent()) {
            BuildingEntity building = existingBuilding.get();
            building.setAddress(updatedBuilding.getAddress());
            building.setHourlyRate(updatedBuilding.getHourlyRate());
            building.setAreaM2(updatedBuilding.getAreaM2());
            building.setCapacity(updatedBuilding.getCapacity());
            building.setDescription(updatedBuilding.getDescription());
            buildingRepository.save(building);
        }
    }

    @Override
    public void deleteBuilding(Long id) {
        buildingRepository.deleteById(id);
    }

    @Override
    public BuildingRating rateBuilding(BuildingDto building, byte rating, UserDto user, String comment) {
        return toDomain(buildingRatingRepository.save(
                        new BuildingRatingEntity(
                                dtoToEntity(building),
                                rating,
                                dtoToEntity(user),
                                comment
                        )
                )
        );
    }

    @Override
    public List<BuildingRating> getAllByBuildingIdAndRating(long buildingId, byte rating) {
        return buildingRatingRepository.findAllByBuildingIdAndRating(buildingId, rating)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Building> getAllByCapacity(int capacity) {
        return buildingRepository.findAllByCapacity(capacity)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public BuildingRating getRatingById(long id) {
        return toDomain(buildingRatingRepository.findById(id)
                .orElseThrow());
    }

    private Building toDomain(BuildingEntity buildingEntity) {
        Hibernate.initialize(buildingEntity.getRating());
        return modelMapper.map(buildingEntity, Building.class);
    }

    private BuildingRating toDomain(BuildingRatingEntity buildingRatingEntity) {
        return modelMapper.map(buildingRatingEntity, BuildingRating.class);
    }

    private BuildingEntity dtoToEntity(BuildingDto buildingDto) {
        return modelMapper.map(buildingDto, BuildingEntity.class);
    }

    private UserEntity dtoToEntity(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }
}
