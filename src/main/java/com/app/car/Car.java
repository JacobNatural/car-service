package com.app.car;

import com.app.color.Color;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

/**
 * Represents a car with various attributes such as brand, model, price, speed, color, and components.
 * Provides methods to query and manipulate these attributes.
 */
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Car {
    /**
     * The brand of the car.
     */
    final String brand;

    /**
     * The model of the car.
     */
    final String model;

    /**
     * The price of the car.
     */
    final BigDecimal price;

    /**
     * The speed of the car.
     */
    final int speed;

    /**
     * The color of the car.
     */
    final Color color;

    /**
     * The list of components of the car.
     */
    final List<String> components;

    /**
     * Checks if the car's speed is within the specified range.
     *
     * @param minSpeed The minimum speed.
     * @param maxSpeed The maximum speed.
     * @return {@code true} if the car's speed is between {@code minSpeed} and {@code maxSpeed}, inclusive;
     *         {@code false} otherwise.
     */
    public boolean hasSpeedBetween(int minSpeed, int maxSpeed) {
        return (speed >= minSpeed && speed <= maxSpeed);
    }

    /**
     * Returns a new {@link Car} object with components sorted according to the given comparator.
     *
     * @param comparator The comparator used to sort the components.
     * @return A new {@link Car} instance with sorted components.
     */
    public Car carWithSortedComponents(Comparator<String> comparator) {
        var sortedComponents = components
                .stream()
                .sorted(comparator)
                .toList();
        return new Car(brand, model, price, speed, color, sortedComponents);
    }

    /**
     * Checks if the car contains the specified component.
     *
     * @param component The component to check.
     * @return {@code true} if the component is present in the car's list of components; {@code false} otherwise.
     */
    public boolean hasComponent(String component) {
        return !components.stream().filter(component::matches)
                .toList()
                .isEmpty();
    }

    /**
     * Calculates the absolute difference between the car's price and the given price.
     *
     * @param price The price to compare against.
     * @return The absolute difference between the car's price and the given price.
     */
    public BigDecimal getDifferentBetweenPrice(BigDecimal price) {
        return this.price.subtract(price).abs();
    }

    /**
     * Checks if the car's price is within the specified range.
     *
     * @param minPrice The minimum price.
     * @param maxPrice The maximum price.
     * @return {@code true} if the car's price is between {@code minPrice} and {@code maxPrice}, inclusive;
     *         {@code false} otherwise.
     */
    public boolean hasPriceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return price.compareTo(minPrice) >= 0 && price.compareTo(maxPrice) <= 0;
    }

    /**
     * Checks if the car's brand matches the specified pattern.
     *
     * @param brand The pattern to match against the car's brand.
     * @return {@code true} if the car's brand matches the pattern; {@code false} otherwise.
     */
    public boolean hasBrandPattern(String brand) {
        return this.brand.matches(brand.toUpperCase());
    }

    /**
     * Checks if the car's model matches the specified pattern.
     *
     * @param model The pattern to match against the car's model.
     * @return {@code true} if the car's model matches the pattern; {@code false} otherwise.
     */
    public boolean hasModelPattern(String model) {
        return this.model.matches(model.toUpperCase());
    }

    /**
     * Checks if the car contains all the specified components.
     *
     * @param components The list of components to check.
     * @return {@code true} if the car's list of components contains all the specified components; {@code false} otherwise.
     */
    public boolean hasComponents(List<String> components) {
        return new HashSet<>(this.components).containsAll(components);
    }

    /**
     * Checks if the car meets the criteria defined by the {@link CarCriterion} object.
     *
     * @param carCriterion The criteria to check against.
     * @return {@code true} if the car meets all the criteria; {@code false} otherwise.
     */
    public boolean hasCarCriterion(CarCriterion carCriterion) {
        return hasModelPattern(carCriterion.model) &&
                hasBrandPattern(carCriterion.brand) &&
                hasSpeedBetween(carCriterion.minSpeed, carCriterion.maxSpeed) &&
                hasPriceBetween(carCriterion.minPrice, carCriterion.maxPrice) &&
                hasComponents(carCriterion.components);
    }
}
