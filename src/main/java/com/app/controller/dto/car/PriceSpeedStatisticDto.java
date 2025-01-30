package com.app.controller.dto.car;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) for holding price and speed statistics of cars.
 * This object contains information about the minimum, maximum, and average price
 * and speed of the cars.
 */
public record PriceSpeedStatisticDto(
        /**
         * The minimum price of the cars in the selected group.
         */
        BigDecimal minPrice,

        /**
         * The maximum price of the cars in the selected group.
         */
        BigDecimal maxPrice,

        /**
         * The average price of the cars in the selected group.
         */
        double avgPrice,

        /**
         * The minimum speed of the cars in the selected group.
         */
        int minSpeed,

        /**
         * The maximum speed of the cars in the selected group.
         */
        int maxSpeed,

        /**
         * The average speed of the cars in the selected group.
         */
        double avgSpeed
) {
}
