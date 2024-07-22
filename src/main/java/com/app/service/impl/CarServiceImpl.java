package com.app.service.impl;

import com.app.car.Car;
import com.app.car.CarMapper;
import com.app.collectors.CarStatisticCollector;
import com.app.collectors.CarMinMaxCollector;
import com.app.color.Color;
import com.app.car.CarCriterion;
import com.app.repository.Repository;
import com.app.service.CarService;
import com.app.statistic.Statistic;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
/**
 * Implementation of the {@link CarService} interface for managing and processing car data.
 * <p>
 * This service provides various methods to query, sort, and process collections of {@link Car} objects.
 * </p>
 */
@ToString
public class CarServiceImpl implements CarService {

    private final Repository<Car> carRepository;

    /**
     * Constructs a new {@link CarServiceImpl} with the specified car repository.
     *
     * @param repository the repository to retrieve car data from
     */
    public CarServiceImpl(Repository<Car> repository) {
        this.carRepository = repository;
    }

    /**
     * Returns a list of cars sorted by the specified comparator.
     *
     * @param comparator the comparator to use for sorting
     * @return a list of cars sorted by the comparator
     * @throws IllegalArgumentException if the comparator is {@code null}
     */
    public List<Car> sortedCarsBy(Comparator<Car> comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator is null");
        }
        return carRepository
                .getAll()
                .stream()
                .sorted(comparator)
                .toList();
    }

    /**
     * Returns a list of cars with speed within the specified interval.
     *
     * @param minSpeed the minimum speed
     * @param maxSpeed the maximum speed
     * @return a list of cars with speed within the interval
     * @throws IllegalArgumentException if {@code minSpeed} is greater than {@code maxSpeed}
     */
    public List<Car> getCarsWithSpeedInterval(int minSpeed, int maxSpeed) {
        if (minSpeed > maxSpeed) {
            throw new IllegalArgumentException("Min speed is greater than max speed");
        }
        return getCarsFilterBy(car -> car.hasSpeedBetween(minSpeed, maxSpeed));
    }

    /**
     * Returns a list of cars filtered by the specified predicate.
     *
     * @param filter the predicate to use for filtering
     * @return a list of cars that match the predicate
     * @throws IllegalArgumentException if the filter is {@code null}
     */
    public List<Car> getCarsFilterBy(Predicate<Car> filter) {
        if (filter == null) {
            throw new IllegalArgumentException("Filter is null");
        }
        return carRepository
                .getAll()
                .stream()
                .filter(filter)
                .toList();
    }

    /**
     * Groups cars by color and counts the number of cars for each color.
     *
     * @return a map of colors to the number of cars for each color
     */
    public Map<Color, Long> groupByColorAndAmountOfCars() {
        return groupByAndAmountOfCars(CarMapper.toColor);
    }

    /**
     * Groups cars by a specified property and counts the number of cars for each property value.
     *
     * @param carMapper a function to map cars to the property to group by
     * @param <T> the type of the property value
     * @return a map of property values to the number of cars for each value
     * @throws IllegalArgumentException if the carMapper is {@code null}
     */
    public <T> Map<T, Long> groupByAndAmountOfCars(Function<Car, T> carMapper) {
        if (carMapper == null) {
            throw new IllegalArgumentException("Car mapper is null");
        }
        return carRepository
                .getAll()
                .stream()
                .collect(Collectors.groupingBy(carMapper, Collectors.counting()));
    }

    /**
     * Groups cars by brand and calculates the minimum and maximum price for each brand.
     *
     * @return a map of brand names to statistics of minimum and maximum prices
     */
    public Map<String, Statistic<BigDecimal>> groupByBrandAndMinMaxPriceStatistic() {
        return groupByAndMinMaxPriceStatistic(
                CarMapper.toBrand,
                new CarMinMaxCollector(CarMapper.toPrice));
    }

    /**
     * Groups cars by a specified property and calculates statistics (e.g., min, max, average) for a specified value.
     *
     * @param carMapper a function to map cars to the property to group by
     * @param collectors a collector to calculate the statistics
     * @param <T> the type of the property value
     * @param <U> the type of the intermediate result of the collector
     * @param <W> the type of the statistics result
     * @return a map of property values to statistics
     * @throws IllegalArgumentException if the carMapper or collectors are {@code null}
     */
    public <T, U, W> Map<T, Statistic<W>> groupByAndMinMaxPriceStatistic(
            Function<Car, T> carMapper, Collector<Car, U, Statistic<W>> collectors) {

        if (carMapper == null) {
            throw new IllegalArgumentException("Car mapper is null");
        }

        if (collectors == null) {
            throw new IllegalArgumentException("Collector is null");
        }

        return carRepository
                .getAll()
                .stream()
                .collect(Collectors.groupingBy(carMapper, collectors));
    }

    /**
     * Returns a list of statistics for price and speed.
     *
     * @return a list containing statistics for price and speed
     */
    public List<Statistic<BigDecimal>> priceSpeedStatistic() {
        return List.of(
                getStatistic(new CarStatisticCollector(CarMapper.toPrice)),
                getStatistic(new CarStatisticCollector(CarMapper.toSpeed)));
    }

    /**
     * Calculates statistics using the specified collector.
     *
     * @param collector the collector to use for calculating statistics
     * @param <T> the type of the intermediate result of the collector
     * @param <W> the type of the statistics result
     * @return the calculated statistics
     * @throws IllegalArgumentException if the collector is {@code null}
     */
    public <T, W> Statistic<W> getStatistic(Collector<Car, T, Statistic<W>> collector) {

        if (collector == null) {
            throw new IllegalArgumentException("Collector is null");
        }

        return carRepository
                .getAll()
                .stream()
                .collect(collector);
    }

    /**
     * Returns a list of cars with their components sorted by the specified comparator.
     *
     * @param comparator the comparator to use for sorting components
     * @return a list of cars with sorted components
     * @throws IllegalArgumentException if the comparator is {@code null}
     */
    public List<Car> getCarsWithSortedComponents(Comparator<String> comparator) {

        if (comparator == null) {
            throw new IllegalArgumentException("Comparator is null");
        }

        return carRepository
                .getAll()
                .stream()
                .map(car -> car.carWithSortedComponents(comparator))
                .toList();
    }

    /**
     * Groups cars by their components and sorts the groups by the number of cars in each group.
     *
     * @param comparator the comparator to use for sorting groups by the number of cars
     * @return a map of component names to lists of cars, sorted by the number of cars in each group
     * @throws IllegalArgumentException if the comparator is {@code null}
     */
    public Map<String, List<Car>> groupByComponentsAndCarsSortedByAmountOfComponents(Comparator<Integer> comparator) {

        if (comparator == null) {
            throw new IllegalArgumentException("Comparator is null");
        }

        return carRepository.getAll()
                .stream()
                .flatMap(
                        car -> CarMapper.toComponents.apply(car).stream()
                                .map(m -> new AbstractMap.SimpleEntry<>(m, car)))
                .collect(Collectors.toMap(
                        AbstractMap.SimpleEntry::getKey,
                        abs -> new ArrayList<>(List.of(abs.getValue())),
                        (v1, v2) -> {
                            v1.addAll(v2);
                            return v1;
                        },
                        LinkedHashMap::new))
                .entrySet()
                .stream()
                .sorted((m1, m2) -> comparator.compare(m1.getValue().size(), m2.getValue().size()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (v1, v2) -> v1, LinkedHashMap::new));
    }

    /**
     * Returns a list of cars whose price is closest to the specified price.
     *
     * @param price the price to compare against
     * @return a list of cars closest to the specified price
     * @throws IllegalArgumentException if the price is {@code null} or less than or equal to zero
     */
    public List<Car> getCarsCloseToPrice(BigDecimal price) {

        if (price == null) {
            throw new IllegalArgumentException("Price is null");
        }

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price is lower than zero");
        }

        return carRepository
                .getAll()
                .stream()
                .collect(Collectors.groupingBy(
                        car -> car.getDifferentBetweenPrice(price), Collectors.toList())
                )
                .entrySet()
                .stream()
                .min(Map.Entry.comparingByKey())
                .orElseThrow()
                .getValue();
    }

    /**
     * Returns a list of cars that meet the specified criterion.
     *
     * @param carCriterion the criterion to filter cars by
     * @return a list of cars that meet the criterion
     * @throws IllegalArgumentException if the carCriterion is {@code null}
     */
    public List<Car> getCarsWithCriterion(CarCriterion carCriterion) {
        if (carCriterion == null) {
            throw new IllegalArgumentException("Car criterion is null");
        }

        return carRepository
                .getAll()
                .stream()
                .filter(car -> car.hasCarCriterion(carCriterion))
                .toList();
    }
}