package com.app.service.impl;

import com.app.controller.dto.car.*;
import com.app.controller.dto.components.ComponentsWithCarsDto;
import com.app.persistence.*;
import com.app.persistence.entity.ComponentEntity;
import com.app.persistence.entity.CarEntity;
import com.app.persistence.impl.CarSpecificationImpl;
import com.app.persistence.view.*;
import com.app.service.CarService;
import com.app.service.impl.generic.CrudServiceGeneric;
import com.app.validate.Validator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The CarServiceImpl class implements the CarService interface and provides
 * functionality for managing car-related operations such as saving, filtering,
 * grouping, and sorting cars.
 * <p>
 * This class interacts with the CarRepository and ComponentRepository to
 * perform CRUD operations on car entities and components. It also supports
 * various advanced operations like grouping cars, getting statistics, and filtering
 * by different criteria such as speed, price, and components.
 * </p>
 */
@Service
@Transactional
public class CarServiceImpl extends CrudServiceGeneric<CarEntity, Long> implements CarService {

    private final CarRepository carRepository;
    private final ComponentRepository componentRepository;
    private final CarSpecificationImpl carSpecificationImpl;
    private final Validator<CreateCarDto> validator;
    private final Validator<String> parametersValidator;

    /**
     * Constructor for CarServiceImpl.
     *
     * @param carRepository        the repository for car entities.
     * @param componentRepository  the repository for component entities.
     * @param carSpecificationImpl the specification implementation for car filtering.
     * @param validator            the validator for CreateCarDto objects.
     * @param parametersValidator  the validator for sorting parameters.
     */
    public CarServiceImpl(
            CarRepository carRepository,
            ComponentRepository componentRepository,
            CarSpecificationImpl carSpecificationImpl,
            Validator<CreateCarDto> validator,
            Validator<String> parametersValidator) {
        super(carRepository);
        this.carRepository = carRepository;
        this.componentRepository = componentRepository;
        this.carSpecificationImpl = carSpecificationImpl;
        this.validator = validator;
        this.parametersValidator = parametersValidator;
    }

    /**
     * Saves a new car entity based on the provided data transfer object (DTO).
     *
     * @param createCarDto the data transfer object containing car information.
     * @return the ID of the newly saved car.
     * @throws EntityNotFoundException if any of the components in the DTO are not found.
     */
    @Override
    public Long save(CreateCarDto createCarDto) {
        validator.validate(createCarDto);
        var components = componentRepository.findAllById(createCarDto.components());

        if (components.size() != createCarDto.components().size()) {
            throw new EntityNotFoundException("Not all components were found");
        }

        return carRepository
                .save(createCarDto
                        .toCarEntity()
                        .withComponents(components))
                .getId();
    }

    /**
     * Saves a list of car entities based on the provided list of DTOs.
     *
     * @param cars the list of data transfer objects representing cars.
     * @return a list of IDs of the newly saved cars.
     * @throws EntityNotFoundException if any of the components in the DTOs are not found.
     */
    @Override
    public List<Long> saveAll(List<CreateCarDto> cars) {
        var componentsIds = cars
                .stream()
                .peek(validator::validate)
                .map(CreateCarDto::components)
                .flatMap(Collection::stream)
                .distinct()
                .toList();

        var components = componentRepository
                .findAllById(componentsIds)
                .stream()
                .collect(Collectors
                        .toMap(
                                ComponentEntity::getId,
                                Function.identity()));

        if (componentsIds.size() != components.size()) {
            throw new EntityNotFoundException("Not all components were found");
        }

        var carsToSave = cars
                .stream()
                .map(createCarDto -> {
                    var componentsEntity = createCarDto
                            .components()
                            .stream()
                            .map(components::get)
                            .toList();
                    return createCarDto.toCarEntity().withComponents(componentsEntity);
                })
                .toList();

        return carRepository.saveAll(carsToSave)
                .stream()
                .map(CarEntity::getId)
                .toList();
    }

    /**
     * Returns a list of cars sorted by specified parameters and order direction.
     *
     * @param parameters a list of parameters to sort the cars by.
     * @param direction  the direction of sorting, either "asc" for ascending or "desc" for descending.
     * @return a list of sorted car DTOs.
     * @throws IllegalArgumentException if parameters or direction are invalid.
     */
    @Override
    public List<CarDto> sortedCarsBy(List<String> parameters, String direction) {
        // Validate input and prepare sorting logic
        if (parameters == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }

        if (parameters.isEmpty()) {
            throw new IllegalArgumentException("Parameters cannot be empty");
        }

        if (direction == null) {
            throw new IllegalArgumentException("Direction cannot be null");
        }

        if (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc")) {
            throw new IllegalArgumentException("Direction must be 'asc' or 'desc'.");
        }

        var direct = Sort.Direction.fromString(direction);
        var sortBy = Sort.unsorted();

        for (var param : parameters) {
            parametersValidator.validate(param);
            sortBy = sortBy.and(Sort.by(direct, param));
        }

        return carRepository
                .findAll(sortBy)
                .stream()
                .map(CarEntity::toCarDto)
                .toList();
    }

    /**
     * Returns a list of cars filtered by a given speed interval.
     *
     * @param minSpeed the minimum speed.
     * @param maxSpeed the maximum speed.
     * @return a list of car DTOs matching the speed interval.
     * @throws IllegalArgumentException if speed values are invalid.
     */
    @Override
    public List<CarDto> getCarsWithSpeedInterval(int minSpeed, int maxSpeed) {
        if (minSpeed < 0) {
            throw new IllegalArgumentException("Min speed cannot be negative");
        }
        if (minSpeed > maxSpeed) {
            throw new IllegalArgumentException("Min speed cannot be greater than max speed");
        }

        return carRepository.findCarEntitiesBySpeedBetween(minSpeed, maxSpeed)
                .stream()
                .map(CarEntity::toCarDto)
                .toList();
    }

    /**
     * Returns a list of cars filtered by the provided criteria.
     *
     * @param carCriterionDto the filtering criteria.
     * @return a list of car DTOs matching the criteria.
     * @throws IllegalArgumentException if the criteria is invalid.
     */
    public List<CarDto> getCarsFilterBy(CarCriterionDto carCriterionDto) {
        if (carCriterionDto == null) {
            throw new IllegalArgumentException("Criterion cannot be null");
        }

        return carRepository.findAll(carSpecificationImpl.dynamicFilters(carCriterionDto.toFilteringView()))
                .stream()
                .map(CarEntity::toCarDto)
                .toList();
    }

    /**
     * Groups cars by a specific field and returns the result as DTOs.
     *
     * @param map the field to group by.
     * @return a list of grouped car DTOs.
     */
    @Override
    public List<GroupByDto<Object>> groupByAndAmountOfCars(String map) {
        parametersValidator.validate(map);

        return carRepository.groupByField(map)
                .stream()
                .map(GroupByView::toGroupByDto)
                .toList();
    }

    /**
     * Groups cars by a specific field and returns statistics such as min and max price.
     *
     * @param map the field to group by.
     * @return a list of grouped car statistics DTOs.
     */
    @Override
    public List<GroupByAndPriceStatisticDto<Object>> groupByAndMinMaxPriceStatistic(String map) {
        parametersValidator.validate(map);

        return carRepository.groupByAndPriceStatisticField(map)
                .stream()
                .map(GroupByAndPriceStatisticView::toGroupByAndPriceStatisticDto)
                .toList();
    }

    /**
     * Returns price and speed statistics for cars.
     *
     * @return the price and speed statistics DTO.
     * @throws EntityNotFoundException if no statistics are found.
     */
    @Override
    public PriceSpeedStatisticDto priceSpeedStatistic() {
        return carRepository
                .findPriceSpeedStatistics()
                .orElseThrow(() -> new EntityNotFoundException("Not found price and speed statistic"))
                .toPriceSpeedStatisticDto();
    }

    /**
     * Returns a list of cars sorted by their components.
     *
     * @param order the sorting order, either "asc" or "desc".
     * @return a list of car DTOs with sorted components.
     */
    @Override
    public List<CarDto> getCarsWithSortedComponents(String order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        Comparator<String> comparator = getComparator(
                order,
                (v1, v2) -> v1.compareTo(v2),
                (v1, v2) -> v2.compareTo(v1)
        );

        return carRepository
                .findAll()
                .stream()
                .map(carEntity ->
                        new CarDto(
                                carEntity.getId(), carEntity.getBrand(),
                                carEntity.getModel(), carEntity.getSpeed(),
                                carEntity.getPrice(), carEntity.getColor(),
                                carEntity
                                        .getComponents()
                                        .stream()
                                        .map(ComponentEntity::getName)
                                        .sorted(comparator)
                                        .toList())
                )
                .toList();
    }

    /**
     * Groups components and their associated cars, sorted by the number of cars.
     *
     * @param order the sorting order, either "asc" or "desc".
     * @return a list of grouped components with associated cars.
     */
    @Override
    public List<ComponentsWithCarsDto> groupByComponentsAndCarsSortedByAmountOfCars(String order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        var comparator = getComparator(
                order,
                Comparator.comparingInt((ComponentsWithCarsDto v) -> v.carDto().size()),
                (v1, v2) -> Integer.compare(v2.carDto().size(), v1.carDto().size()));

        return componentRepository
                .groupByComponent()
                .stream()
                .collect(Collectors.toMap(
                        ComponentsAndCarsView::name,
                        element ->
                                new ArrayList<>(List.of(new CarWithoutComponentsDto(
                                        element.id(), element.brand(),
                                        element.model(), element.speed(),
                                        element.price(), element.color()))),
                        (v1, v2) -> {
                            v1.addAll(v2);
                            return v1;
                        }))
                .entrySet()
                .stream()
                .map(map -> new ComponentsWithCarsDto(map.getKey(), map.getValue()))
                .sorted(comparator)
                .toList();
    }

    /**
     * Returns a list of cars that are close to a given price.
     *
     * @param price the target price.
     * @return a list of car DTOs close to the target price.
     * @throws IllegalArgumentException if the price is invalid.
     */
    @Override
    public List<CarDto> getCarsCloseToPrice(BigDecimal price) {
        if (price == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        return carRepository
                .findCarsCloseToPrice(price)
                .stream()
                .map(CarEntity::toCarDto)
                .toList();
    }

    /**
     * Helper method to get a comparator based on the order.
     *
     * @param order   the order (either "asc" or "desc").
     * @param natural the comparator for ascending order.
     * @param reverse the comparator for descending order.
     * @param <T>     the type of the objects being compared.
     * @return the comparator based on the order.
     */
    private <T> Comparator<T> getComparator(String order, Comparator<T> natural, Comparator<T> reverse) {
        return switch (order) {
            case "asc" -> natural;
            case "desc" -> reverse;
            default -> throw new IllegalArgumentException("Invalid order: " + order);
        };
    }
}
