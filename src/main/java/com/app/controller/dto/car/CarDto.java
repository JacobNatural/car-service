package com.app.controller.dto.car;

import com.app.color.Color;

import java.math.BigDecimal;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing a car.
 * Contains the details of a car including its ID, brand, model, speed, price, color, and components.
 */
public record CarDto(
        /**
         * The unique identifier for the car.
         */
        Long id,

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
         * A list of components associated with the car (e.g., airbags, sunroof).
         */
        List<String> components
) { }
