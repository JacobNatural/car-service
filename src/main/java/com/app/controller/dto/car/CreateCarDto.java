package com.app.controller.dto.car;

import com.app.color.Color;
import com.app.persistence.entity.CarEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Data Transfer Object (DTO) used for creating a car.
 * It contains the necessary information to create a new car entity, including its brand, model,
 * speed, price, color, and associated components.
 */
public record CreateCarDto(
        /**
         * The brand of the car (e.g., Toyota, Ford).
         */
        String brand,

        /**
         * The model of the car (e.g., Corolla, Mustang).
         */
        String model,

        /**
         * The speed of the car, in kilometers per hour (km/h).
         */
        int speed,

        /**
         * The price of the car.
         */
        BigDecimal price,

        /**
         * The color of the car.
         */
        Color color,

        /**
         * List of IDs of components associated with the car.
         * This is used for linking the car to its components, although not included in the entity.
         */
        List<Long> components
) {

    /**
     * Converts the CreateCarDto to a CarEntity object.
     * This is used to map the DTO to an entity for persistence in the database.
     *
     * @return The corresponding CarEntity object.
     */
    public CarEntity toCarEntity() {
        return CarEntity
                .builder()
                .brand(brand)
                .model(model)
                .speed(speed)
                .price(price)
                .color(color)
                .build();
    }
}

