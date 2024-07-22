package com.app.car;

import com.app.color.Color;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

/**
 * Defines a set of {@link Function} mappings for extracting various attributes from a {@link Car} object.
 * These mappings allow for easy transformation of {@link Car} instances into different types of data.
 */
public interface CarMapper {
    /**
     * A {@link Function} that extracts the {@link Color} of a {@link Car}.
     */
    Function<Car, Color> toColor = car -> car.color;

    /**
     * A {@link Function} that extracts the model of a {@link Car} as a {@link String}.
     */
    Function<Car, String> toModel = car -> car.model;

    /**
     * A {@link Function} that extracts the price of a {@link Car} as a {@link BigDecimal}.
     */
    Function<Car, BigDecimal> toPrice = car -> car.price;

    /**
     * A {@link Function} that extracts the brand of a {@link Car} as a {@link String}.
     */
    Function<Car, String> toBrand = car -> car.brand;

    /**
     * A {@link Function} that extracts the speed of a {@link Car} and converts it to a {@link BigDecimal}.
     */
    Function<Car, BigDecimal> toSpeed = car -> BigDecimal.valueOf(car.speed);

    /**
     * A {@link Function} that extracts the list of components of a {@link Car} as a {@link List} of {@link String}.
     */
    Function<Car, List<String>> toComponents = car -> car.components;
}