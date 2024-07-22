package com.app.service;

import com.app.car.Car;
import com.app.color.Color;
import com.app.car.CarCriterion;
import com.app.statistic.Statistic;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Interface for car-related services that provides methods for querying and processing car data.
 * <p>
 * This service includes operations for sorting, filtering, grouping, and computing statistics on a collection of {@link Car} objects.
 * </p>
 */
public interface CarService {

    /**
     * Returns a list of cars sorted by the specified comparator.
     *
     * @param comparator the comparator to use for sorting the cars
     * @return a list of cars sorted according to the comparator
     * @throws IllegalArgumentException if the comparator is {@code null}
     */
    List<Car> sortedCarsBy(Comparator<Car> comparator);

    /**
     * Returns a list of cars whose speed is within the specified interval.
     *
     * @param minSpeed the minimum speed
     * @param maxSpeed the maximum speed
     * @return a list of cars with speeds within the specified interval
     * @throws IllegalArgumentException if {@code minSpeed} is greater than {@code maxSpeed}
     */
    List<Car> getCarsWithSpeedInterval(int minSpeed, int maxSpeed);

    /**
     * Groups cars by color and counts the number of cars for each color.
     *
     * @return a map where the keys are car colors and the values are the counts of cars for each color
     */
    Map<Color, Long> groupByColorAndAmountOfCars();

    /**
     * Groups cars by brand and calculates the minimum and maximum price for each brand.
     *
     * @return a map where the keys are brand names and the values are statistics of minimum and maximum prices for each brand
     */
    Map<String, Statistic<BigDecimal>> groupByBrandAndMinMaxPriceStatistic();

    /**
     * Retrieves statistics for car prices and speeds.
     *
     * @return a list of statistics containing information about prices and speeds
     */
    List<Statistic<BigDecimal>> priceSpeedStatistic();

    /**
     * Returns a list of cars with their components sorted by the specified comparator.
     *
     * @param comparator the comparator to use for sorting the car components
     * @return a list of cars with sorted components
     * @throws IllegalArgumentException if the comparator is {@code null}
     */
    List<Car> getCarsWithSortedComponents(Comparator<String> comparator);

    /**
     * Groups cars by their components and sorts the groups by the number of cars in each group.
     *
     * @param comparator the comparator to use for sorting the groups by the number of cars
     * @return a map where the keys are component names and the values are lists of cars, sorted by the number of cars in each group
     * @throws IllegalArgumentException if the comparator is {@code null}
     */
    Map<String, List<Car>> groupByComponentsAndCarsSortedByAmountOfComponents(Comparator<Integer> comparator);

    /**
     * Returns a list of cars whose price is closest to the specified price.
     *
     * @param price the price to compare against
     * @return a list of cars with prices closest to the specified price
     * @throws IllegalArgumentException if the price is {@code null} or less than or equal to zero
     */
    List<Car> getCarsCloseToPrice(BigDecimal price);

    /**
     * Returns a list of cars that meet the specified criterion.
     *
     * @param carCriterion the criterion to use for filtering cars
     * @return a list of cars that meet the criterion
     * @throws IllegalArgumentException if the carCriterion is {@code null}
     */
    List<Car> getCarsWithCriterion(CarCriterion carCriterion);
}