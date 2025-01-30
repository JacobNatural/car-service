package com.app.persistence.view;

import com.app.color.Color;

import java.math.BigDecimal;

/**
 * A record representing a view for a component and the cars associated with it.
 * <p>
 * This record combines information about a car and a component, providing details such as the component name,
 * car ID, brand, model, color, price, and speed.
 * </p>
 */
public record ComponentsAndCarsView(
        String name,           // The name of the component.
        Long id,               // The ID of the component.
        String brand,          // The brand of the car.
        String model,          // The model of the car.
        Color color,           // The color of the car.
        BigDecimal price,      // The price of the car.
        int speed) {           // The speed of the car.

    /**
     * Constructs a new {@link ComponentsAndCarsView} with the provided details for the component and car.
     *
     * @param name    the name of the component.
     * @param id      the ID of the component.
     * @param brand   the brand of the car.
     * @param model   the model of the car.
     * @param color   the color of the car.
     * @param price   the price of the car.
     * @param speed   the speed of the car.
     */
}
