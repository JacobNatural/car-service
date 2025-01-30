package com.app.service;

import com.app.controller.dto.car.*;
import com.app.controller.dto.components.ComponentsWithCarsDto;
import com.app.persistence.entity.CarEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface CarService defines the operations for managing car entities.
 * It extends the CrudService interface to provide basic CRUD functionalities.
 * Additionally, it includes methods for handling specific car-related queries
 * such as saving, filtering, sorting, and aggregating car data.
 * <p>
 * This service is responsible for performing operations like saving cars,
 * getting cars within a specific speed interval, grouping cars by certain
 * criteria, retrieving statistics, and more.
 * </p>
 */
public interface CarService extends CrudService<CarEntity, Long> {

    /**
     * Saves a new car based on the provided data transfer object (DTO).
     *
     * @param carDto the data transfer object containing the car information.
     * @return the ID of the newly saved car.
     */
    Long save(CreateCarDto carDto);

    /**
     * Saves a list of cars based on the provided list of data transfer objects (DTOs).
     *
     * @param cars the list of data transfer objects representing cars.
     * @return a list of IDs of the newly saved cars.
     */
    List<Long> saveAll(List<CreateCarDto> cars);

    /**
     * Retrieves a list of cars sorted by the specified parameters and direction.
     *
     * @param parameters the list of parameters by which to sort the cars.
     * @param direction the direction of sorting ('asc' for ascending, 'desc' for descending).
     * @return a list of sorted car DTOs.
     */
    List<CarDto> sortedCarsBy(List<String> parameters, String direction);

    /**
     * Retrieves a list of cars within the specified speed interval.
     *
     * @param minSpeed the minimum speed of the cars to retrieve.
     * @param maxSpeed the maximum speed of the cars to retrieve.
     * @return a list of car DTOs within the specified speed range.
     */
    List<CarDto> getCarsWithSpeedInterval(int minSpeed, int maxSpeed);

    /**
     * Groups cars by a specific field and returns the number of cars in each group.
     *
     * @param map the field by which to group the cars.
     * @return a list of group-by DTOs containing the field and the amount of cars in each group.
     */
    List<GroupByDto<Object>> groupByAndAmountOfCars(String map);

    /**
     * Groups cars by a specific field and returns the minimum and maximum price for each group.
     *
     * @param map the field by which to group the cars.
     * @return a list of group-by DTOs containing the field and price statistics (min/max).
     */
    List<GroupByAndPriceStatisticDto<Object>> groupByAndMinMaxPriceStatistic(String map);

    /**
     * Retrieves the price and speed statistics for all cars (minimum, maximum, and average values).
     *
     * @return the price and speed statistics DTO.
     */
    PriceSpeedStatisticDto priceSpeedStatistic();

    /**
     * Retrieves a list of cars with their components sorted according to the specified order.
     *
     * @param order the sorting order ('asc' for ascending, 'desc' for descending).
     * @return a list of car DTOs with sorted components.
     */
    List<CarDto> getCarsWithSortedComponents(String order);

    /**
     * Groups cars by their components and sorts them based on the amount of cars per component.
     *
     * @param order the sorting order ('asc' for ascending, 'desc' for descending).
     * @return a list of DTOs containing components and the associated cars sorted by the number of cars per component.
     */
    List<ComponentsWithCarsDto> groupByComponentsAndCarsSortedByAmountOfCars(String order);

    /**
     * Retrieves a list of cars whose prices are closest to the specified price.
     *
     * @param price the price to compare with.
     * @return a list of car DTOs whose prices are closest to the specified price.
     */
    List<CarDto> getCarsCloseToPrice(BigDecimal price);

    /**
     * Retrieves a list of cars filtered by the provided criteria.
     *
     * @param carCriterionDto the criteria used for filtering the cars.
     * @return a list of filtered car DTOs.
     */
    List<CarDto> getCarsFilterBy(CarCriterionDto carCriterionDto);
}
