package com.app.controller;

import com.app.controller.dto.*;
import com.app.controller.dto.car.*;
import com.app.controller.dto.components.ComponentsWithCarsDto;
import com.app.persistence.entity.CarEntity;
import com.app.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * REST controller for managing cars.
 * Provides endpoints for creating, updating, retrieving, deleting, and sorting cars.
 * Also includes filtering and statistics functionality related to cars.
 */
@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    /**
     * Creates a new car and returns its ID.
     *
     * @param createCarDto Data required to create a car.
     * @return ResponseDto containing the ID of the created car.
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto<Long> createCar(@RequestBody CreateCarDto createCarDto) {
        return new ResponseDto<>(carService.save(createCarDto));
    }

    /**
     * Creates multiple new cars and returns their IDs.
     *
     * @param createCarDtos List of data required to create multiple cars.
     * @return ResponseDto containing the list of IDs of the created cars.
     */
    @PostMapping("/all")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto<List<Long>> createCars(@RequestBody List<CreateCarDto> createCarDtos) {
        return new ResponseDto<>(carService.saveAll(createCarDtos));
    }

    /**
     * Retrieves a car by its ID.
     *
     * @param id The ID of the car to retrieve.
     * @return ResponseDto containing the car data.
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseDto<CarDto> findCarById(@PathVariable Long id) {
        return new ResponseDto<>(carService.findById(id).toCarDto());
    }

    /**
     * Retrieves a list of cars by their IDs.
     *
     * @param ids List of car IDs.
     * @return ResponseDto containing a list of car data.
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseDto<List<CarDto>> findAllCars(@RequestParam List<Long> ids) {
        return new ResponseDto<>(carService
                .findAllById(ids)
                .stream()
                .map(CarEntity::toCarDto)
                .toList());
    }

    /**
     * Deletes a car by its ID.
     *
     * @param id The ID of the car to delete.
     * @return ResponseDto containing the ID of the deleted car.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<Long> deleteCarById(@PathVariable Long id) {
        return new ResponseDto<>(carService.deleteById(id));
    }

    /**
     * Deletes multiple cars by their IDs.
     *
     * @param ids List of car IDs to delete.
     * @return ResponseDto containing a list of deleted car IDs.
     */
    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<List<Long>> deleteCarById(@RequestParam List<Long> ids) {
        return new ResponseDto<>(carService.deleteAllById(ids));
    }

    /**
     * Retrieves a list of cars sorted by specified parameters and direction.
     *
     * @param parameters List of parameters to sort by.
     * @param direction  Sorting direction ("asc" or "desc").
     * @return ResponseDto containing a list of sorted car data.
     */
    @GetMapping("/sort")
    public ResponseDto<List<CarDto>> getSortedCarAsc(@RequestParam List<String> parameters, @RequestParam String direction) {
        return new ResponseDto<>(carService.sortedCarsBy(parameters, direction));
    }

    /**
     * Retrieves a list of cars with speed between specified minimum and maximum values.
     *
     * @param minSpeed Minimum speed.
     * @param maxSpeed Maximum speed.
     * @return ResponseDto containing a list of cars within the speed range.
     */
    @GetMapping("/{minSpeed}/{maxSpeed}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<List<CarDto>> getCarsWithSpeedBetween(
            @PathVariable Integer minSpeed,
            @PathVariable Integer maxSpeed) {
        return new ResponseDto<>(carService.getCarsWithSpeedInterval(minSpeed, maxSpeed));
    }

    /**
     * Retrieves a list of cars based on the specified filter criteria.
     *
     * @param carCriterionDto Filtering criteria for the cars.
     * @return ResponseDto containing a list of cars that match the filter.
     */
    @PostMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<List<CarDto>> getCarsFiltersBy(@RequestBody CarCriterionDto carCriterionDto) {
        return new ResponseDto<>(carService.getCarsFilterBy(carCriterionDto));
    }

    /**
     * Groups cars by color and returns the number of cars in each group.
     *
     * @return ResponseDto containing the grouped cars by color and their counts.
     */
    @GetMapping("/color")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<List<GroupByDto<Object>>> groupByColorAndAmount() {
        return new ResponseDto<>(carService.groupByAndAmountOfCars("color"));
    }

    /**
     * Groups cars by the specified parameter and returns the number of cars in each group.
     *
     * @param map The parameter by which to group the cars (e.g., "color").
     * @return ResponseDto containing the grouped cars and their counts.
     */
    @GetMapping("/group/{map}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<List<GroupByDto<Object>>> groupByAndAmount(@PathVariable String map) {
        return new ResponseDto<>(carService.groupByAndAmountOfCars(map));
    }

    /**
     * Retrieves the price statistics (min, max, average) for cars grouped by a specified parameter.
     *
     * @return ResponseDto containing the price statistics.
     */
    @GetMapping("/statistic/price")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<List<GroupByAndPriceStatisticDto<Object>>> groupByBrandAndMinMaxPriceStatistic() {
        return new ResponseDto<>(carService.groupByAndMinMaxPriceStatistic("brand"));
    }

    /**
     * Retrieves the price statistics (min, max, average) for cars grouped by a specified parameter.
     *
     * @param map The parameter by which to group the cars (e.g., "price").
     * @return ResponseDto containing the price statistics.
     */
    @GetMapping("/price/{map}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<List<GroupByAndPriceStatisticDto<Object>>> groupByAndMinMaxPriceStatistic(@PathVariable String map) {
        return new ResponseDto<>(carService.groupByAndMinMaxPriceStatistic(map));
    }

    /**
     * Retrieves the price and speed statistics (min, max, average) for all cars.
     *
     * @return ResponseDto containing the price and speed statistics.
     */
    @GetMapping("/price-speed")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<PriceSpeedStatisticDto> priceSpeedStatistic() {
        return new ResponseDto<>(carService.priceSpeedStatistic());
    }

    /**
     * Retrieves cars sorted by components in a specified order (natural or custom).
     *
     * @param order The order to sort the cars (e.g., "asc" or "desc").
     * @return ResponseDto containing the sorted cars.
     */
    @GetMapping("/sort/components/{order}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<List<CarDto>> getCarsWithSortedComponentsNatural(@PathVariable String order) {
        return new ResponseDto<>(carService.getCarsWithSortedComponents(order));
    }

    /**
     * Retrieves cars grouped by components and sorted by the number of cars in each group.
     *
     * @param order The order to sort the groups (e.g., "asc" or "desc").
     * @return ResponseDto containing the grouped cars by components.
     */
    @GetMapping("/components/{order}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<List<ComponentsWithCarsDto>> getCarsWithSortedGroupComponentsNatural(@PathVariable String order) {
        return new ResponseDto<>(carService.groupByComponentsAndCarsSortedByAmountOfCars(order));
    }

    /**
     * Retrieves cars with a price close to a specified value.
     *
     * @param price The price to compare against.
     * @return ResponseDto containing the cars close to the specified price.
     */
    @GetMapping("/close/{price}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<List<CarDto>> getCarCloseToPrice(@PathVariable BigDecimal price) {
        return new ResponseDto<>(carService.getCarsCloseToPrice(price));
    }
}
