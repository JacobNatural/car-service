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
 * The type Car.
 */
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Car {
    /**
     * The Brand.
     */
    final String brand;
    /**
     * The Model.
     */
    final String model;
    /**
     * The Price.
     */
    final BigDecimal price;
    /**
     * The Speed.
     */
    final int speed;
    /**
     * The Color.
     */
    final Color color;
    /**
     * The Components.
     */
    final List<String> components;

    /**
     * Has speed between boolean.
     *
     * @param minSpeed the min speed
     * @param maxSpeed the max speed
     * @return the boolean
     */
    public boolean hasSpeedBetween(int minSpeed, int maxSpeed) {

        return (speed >= minSpeed && speed <= maxSpeed);
    }

    /**
     * Car with sorted components car.
     *
     * @param comparator the comparator
     * @return the car
     */
    public Car carWithSortedComponents(Comparator<String> comparator) {

        var sortedComponents = components
                .stream()
                .sorted(comparator)
                .toList();

        return new Car(brand, model, price, speed, color, sortedComponents);
    }

    /**
     * Has component boolean.
     *
     * @param component the component
     * @return the boolean
     */
    public boolean hasComponent(String component) {

        return !components.stream().filter(component::matches)
                .toList()
                .isEmpty();
    }


    /**
     * Gets different between price.
     *
     * @param price the price
     * @return the different between price
     */
    public BigDecimal getDifferentBetweenPrice(BigDecimal price) {

        return this.price.subtract(price).abs();
    }

    /**
     * Has price between boolean.
     *
     * @param minPrice the min price
     * @param maxPrice the max price
     * @return the boolean
     */
    public boolean hasPriceBetween(BigDecimal minPrice, BigDecimal maxPrice) {

        return price.compareTo(minPrice) >= 0 && price.compareTo(maxPrice) <= 0;
    }

    /**
     * Has brand pattern boolean.
     *
     * @param brand the brand
     * @return the boolean
     */
    public boolean hasBrandPattern(String brand) {

        return this.brand.matches(brand.toUpperCase());
    }

    /**
     * Has model pattern boolean.
     *
     * @param model the model
     * @return the boolean
     */
    public boolean hasModelPattern(String model) {

        return this.model.matches(model.toUpperCase());
    }

    /**
     * Has components boolean.
     *
     * @param components the components
     * @return the boolean
     */
    public boolean hasComponents(List<String> components) {

        return new HashSet<>(this.components).containsAll(components);
    }

    /**
     * Has car criterion boolean.
     *
     * @param carCriterion the car criterion
     * @return the boolean
     */
    public boolean hasCarCriterion(CarCriterion carCriterion) {

        return hasModelPattern(carCriterion.model) &&
                hasBrandPattern(carCriterion.brand) &&
                hasSpeedBetween(carCriterion.minSpeed, carCriterion.maxSpeed) &&
                hasPriceBetween(carCriterion.minPrice, carCriterion.maxPrice) &&
                hasComponents(carCriterion.components);
    }
}
