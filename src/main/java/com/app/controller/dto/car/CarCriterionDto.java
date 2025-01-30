package com.app.controller.dto.car;

import com.app.color.Color;
import com.app.persistence.view.CarCriterionFilteringView;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents a DTO (Data Transfer Object) that holds car filtering criteria.
 * This class includes information such as brand, model, speed range, price range, color, and components.
 * It is used for filtering cars based on various search parameters.
 */
public record CarCriterionDto(
        String brand, // The brand of the car
        String model, // The model of the car
        Integer minSpeed, // The minimum speed of the car
        Integer maxSpeed, // The maximum speed of the car
        BigDecimal minPrice, // The minimum price of the car
        BigDecimal maxPrice, // The maximum price of the car
        Color color, // The color of the car
        List<String> components // List of components of the car
) {

    /**
     * Converts this `CarCriterionDto` object into a `CarCriterionFilteringView`
     * object that can be used in database queries for car filtering.
     *
     * @return a `CarCriterionFilteringView` object containing the filtering criteria.
     */
    public CarCriterionFilteringView toFilteringView() {
        return new CarCriterionFilteringView(brand, model, minSpeed, maxSpeed,
                minPrice, maxPrice, color, components);
    }
}
