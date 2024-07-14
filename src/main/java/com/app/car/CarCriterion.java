package com.app.car;

import com.app.validate.Validator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;


/**
 * The type Car criterion.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class CarCriterion{
    /**
     * The Brand.
     */
    final String brand;
    /**
     * The Model.
     */
    final String model;
    /**
     * The Min speed.
     */
    final int minSpeed;
    /**
     * The Max speed.
     */
    final int maxSpeed;
    /**
     * The Min price.
     */
    final BigDecimal minPrice;
    /**
     * The Max price.
     */
    final BigDecimal maxPrice;
    /**
     * The Components.
     */
    final List<String> components;

    /**
     * Of car criterion.
     *
     * @param brand      the brand
     * @param model      the model
     * @param minSpeed   the min speed
     * @param maxSpeed   the max speed
     * @param minPrice   the min price
     * @param maxPrice   the max price
     * @param components the components
     * @param validator  the validator
     * @return the car criterion
     */
    public static CarCriterion of(
            String brand, String model,
            int minSpeed, int maxSpeed,
            BigDecimal minPrice, BigDecimal maxPrice,
            List<String> components, Validator<CarCriterion> validator) {

        var carCriterion = new CarCriterion(brand, model, minSpeed, maxSpeed, minPrice, maxPrice,components);
        Validator.validate(carCriterion, validator);

        return carCriterion;
    }
}