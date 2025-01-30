package com.app.persistence.view;

import com.app.color.Color;

import java.math.BigDecimal;
import java.util.List;

/**
 * A record representing the filtering criteria for cars.
 * <p>
 * This record contains various fields that are used to filter car entities based on brand, model, speed, price range,
 * color, and associated components.
 * </p>
 */
public record CarCriterionFilteringView(
        String brand,
        String model,
        Integer minSpeed,
        Integer maxSpeed,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        Color color,
        List<String> components) {

    /**
     * Constructs a new {@link CarCriterionFilteringView} with the provided filtering parameters.
     *
     * @param brand         the brand of the car to filter (can be null to ignore this filter)
     * @param model         the model of the car to filter (can be null to ignore this filter)
     * @param minSpeed      the minimum speed of the car to filter (can be null to ignore this filter)
     * @param maxSpeed      the maximum speed of the car to filter (can be null to ignore this filter)
     * @param minPrice      the minimum price of the car to filter (can be null to ignore this filter)
     * @param maxPrice      the maximum price of the car to filter (can be null to ignore this filter)
     * @param color         the color of the car to filter (can be null to ignore this filter)
     * @param components    a list of components that the car should have (can be null or empty to ignore this filter)
     */
}
