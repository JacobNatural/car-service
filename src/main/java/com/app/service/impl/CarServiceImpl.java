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
 * The type Car service.
 */
@ToString
public class CarServiceImpl implements CarService {

    private final Repository<Car> carRepository;

    /**
     * Instantiates a new Car service.
     *
     * @param repository the repository
     */
    public CarServiceImpl(Repository<Car> repository){
        this.carRepository = repository;
    }

    public List<Car> sortedCarsBy(Comparator<Car> comparator) {

        if(comparator == null){
            throw new IllegalArgumentException("Comparator is null");
        }
        return carRepository
                .getAll()
                .stream()
                .sorted(comparator)
                .toList();
    }

    public List<Car> getCarsWithSpeedInterval(int minSpeed, int maxSpeed) {
        if(minSpeed > maxSpeed){
            throw new IllegalArgumentException("Min speed is greater than max speed");
        }
        return getCarsFilterBy(car -> car.hasSpeedBetween(minSpeed, maxSpeed));
    }

    /**
     * Get cars filter by list.
     *
     * @param filter the filter
     * @return the list
     */
        public List<Car> getCarsFilterBy(Predicate<Car> filter){
        if(filter == null){
            throw new IllegalArgumentException("Filter is null");
        }
        return carRepository
                .getAll()
                .stream()
                .filter(filter)
                .toList();
    }

    public Map<Color, Long> groupByColorAndAmountOfCars(){
        return groupByAndAmountOfCars(CarMapper.toColor);
    }

    /**
     * Group by and amount of cars map.
     *
     * @param <T>       the type parameter
     * @param carMapper the car mapper
     * @return the map
     */
    public   <T> Map<T, Long> groupByAndAmountOfCars(Function<Car, T> carMapper) {
        if(carMapper == null){
            throw new IllegalArgumentException("Car mapper is null");
        }
        return   carRepository
                .getAll()
                .stream()
                .collect(Collectors.groupingBy(carMapper, Collectors.counting()));
    }
    public Map<String, Statistic<BigDecimal>> groupByBrandAndMinMaxPriceStatistic(){
        return groupByAndMinMaxPriceStatistic(
                CarMapper.toBrand,
                new CarMinMaxCollector(CarMapper.toPrice));
    }

    /**
     * Group by and min max price statistic map.
     *
     * @param <T>        the type parameter
     * @param <U>        the type parameter
     * @param <W>        the type parameter
     * @param carMapper  the car mapper
     * @param collectors the collectors
     * @return the map
     */
    public   <T, U, W> Map<T, Statistic<W>> groupByAndMinMaxPriceStatistic(
            Function<Car, T> carMapper, Collector<Car, U, Statistic<W>> collectors) {

        if(carMapper == null){
            throw new IllegalArgumentException("Car mapper is null");
        }

        if(collectors == null){
            throw new IllegalArgumentException("Collector is null");
        }

        return carRepository
                .getAll()
                .stream()
                .collect(Collectors.groupingBy(carMapper, collectors));
    }


    public List<Statistic<BigDecimal>> priceSpeedStatistic(){
        return List.of(
                getStatistic( new CarStatisticCollector(CarMapper.toPrice)),
                getStatistic(new CarStatisticCollector(CarMapper.toSpeed)));
    }

    /**
     * Get statistic .
     *
     * @param <T>       the type parameter
     * @param <W>       the type parameter
     * @param collector the collector
     * @return the statistic
     */
    public  <T, W> Statistic<W> getStatistic(Collector<Car, T, Statistic<W>> collector){

        if(collector == null){
            throw new IllegalArgumentException("Collector is null");
        }

        return carRepository
                .getAll()
                .stream()
                .collect(collector);
    }

    public List<Car> getCarsWithSortedComponents(Comparator<String> comparator) {

        if(comparator == null){
            throw new IllegalArgumentException("Comparator is null");
        }

        return carRepository
                .getAll()
                .stream()
                .map(car -> car.carWithSortedComponents(comparator))
                .toList();
    }


    public Map<String, List<Car>>  groupByComponentsAndCarsSortedByAmountOfComponents(Comparator<Integer> comparator){

        if(comparator == null){
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

    public List<Car> getCarsCloseToPrice(BigDecimal price) {

        if(price == null){
            throw new IllegalArgumentException("Price is null");
        }

        if(price.compareTo(BigDecimal.ZERO) <= 0){
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
    public List<Car> getCarsWithCriterion(CarCriterion carCriterion) {
        if(carCriterion == null){
            throw new IllegalArgumentException("Car criterion is null");
        }

        return carRepository
                .getAll()
                .stream()
                .filter(car -> car.hasCarCriterion(carCriterion))
                .toList();
    }
}