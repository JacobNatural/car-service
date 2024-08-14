package com.app.car;

import com.app.validate.Validator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents the criteria for filtering or querying cars based on various attributes such as brand, model,
 * speed, price, and components.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Builder
public class CarCriterion {
    /**
     * The brand of the car to filter by.
     */
    final String brand;

    /**
     * The model of the car to filter by.
     */
    final String model;

    /**
     * The minimum speed of the car to filter by.
     */
    final int minSpeed;

    /**
     * The maximum speed of the car to filter by.
     */
    final int maxSpeed;

    /**
     * The minimum price of the car to filter by.
     */
    final BigDecimal minPrice;

    /**
     * The maximum price of the car to filter by.
     */
    final BigDecimal maxPrice;

    /**
     * The list of components that the car must have to match the criteria.
     */
    final List<String> components;

    /**
     * Creates a new instance of {@link CarCriterion} and validates it using the provided validator.
     *
     * @param brand The brand of the car.
     * @param model The model of the car.
     * @param minSpeed The minimum speed of the car.
     * @param maxSpeed The maximum speed of the car.
     * @param minPrice The minimum price of the car.
     * @param maxPrice The maximum price of the car.
     * @param components The list of components that the car must have.
     * @param validator The validator used to validate the {@link CarCriterion} instance.
     * @return A new {@link CarCriterion} instance if validation succeeds.
     * @throws IllegalArgumentException If validation fails.
     */
    public static CarCriterion of(
            String brand, String model,
            int minSpeed, int maxSpeed,
            BigDecimal minPrice, BigDecimal maxPrice,
            List<String> components, Validator<CarCriterion> validator) {

        var carCriterion = new CarCriterion(brand, model, minSpeed, maxSpeed, minPrice, maxPrice, components);
        Validator.validate(carCriterion, validator);

        return carCriterion;
    }
}