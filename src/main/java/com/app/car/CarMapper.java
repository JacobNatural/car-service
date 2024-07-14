package com.app.car;

import com.app.color.Color;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

/**
 * The interface Car mapper.
 */
public interface CarMapper {
    /**
     * The To color.
     */
    Function<Car, Color> toColor = car -> car.color;
    /**
     * The To model.
     */
    Function<Car, String> toModel = car -> car.model;
    /**
     * The To price.
     */
    Function<Car, BigDecimal> toPrice = car -> car.price;
    /**
     * The To brand.
     */
    Function<Car, String> toBrand = car -> car.brand;
    /**
     * The To speed.
     */
    Function<Car, BigDecimal> toSpeed = car -> BigDecimal.valueOf(car.speed);
    /**
     * The To components.
     */
    Function<Car, List<String>> toComponents = car -> car.components;
}
