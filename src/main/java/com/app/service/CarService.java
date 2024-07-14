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
 * The interface Car service.
 */
public interface CarService {

    /**
     * Sorted cars by list.
     *
     * @param comparator the comparator
     * @return the list
     */
    List<Car> sortedCarsBy(Comparator<Car> comparator);

    /**
     * Gets cars with speed interval.
     *
     * @param minSpeed the min speed
     * @param maxSpeed the max speed
     * @return the cars with speed interval
     */
    List<Car> getCarsWithSpeedInterval(int minSpeed, int maxSpeed);

    /**
     * Group by color and amount of cars map.
     *
     * @return the map
     */
    Map<Color, Long> groupByColorAndAmountOfCars();

    /**
     * Group by brand and min max price statistic map.
     *
     * @return the map
     */
    Map<String, Statistic<BigDecimal>> groupByBrandAndMinMaxPriceStatistic();

    /**
     * Price speed statistic list.
     *
     * @return the list
     */
    List<Statistic<BigDecimal>> priceSpeedStatistic();

    /**
     * Gets cars with sorted components.
     *
     * @param comparator the comparator
     * @return the cars with sorted components
     */
    List<Car> getCarsWithSortedComponents(Comparator<String> comparator);

    /**
     * Group by components and cars sorted by amount of components map.
     *
     * @param comparator the comparator
     * @return the map
     */
    Map<String, List<Car>> groupByComponentsAndCarsSortedByAmountOfComponents(Comparator<Integer> comparator);

    /**
     * Gets cars close to price.
     *
     * @param price the price
     * @return the cars close to price
     */
    List<Car> getCarsCloseToPrice(BigDecimal price);

    /**
     * Gets cars with criterion.
     *
     * @param carCriterion the car criterion
     * @return the cars with criterion
     */
    List<Car> getCarsWithCriterion(CarCriterion carCriterion);
}
