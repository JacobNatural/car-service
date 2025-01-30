package com.app.persistence.view;

import com.app.controller.dto.car.PriceSpeedStatisticDto;

import java.math.BigDecimal;

/**
 * A record representing the statistical information of cars, specifically related to price and speed.
 * <p>
 * This view holds aggregated statistics such as the minimum, maximum, and average price and speed of the cars
 * in a certain context (e.g., a group of cars filtered by some criteria).
 * </p>
 */
public record PriceSpeedStatisticView(
        BigDecimal minPrice,    // The minimum price of the cars in the group.
        BigDecimal maxPrice,    // The maximum price of the cars in the group.
        double avgPrice,        // The average price of the cars in the group.
        int minSpeed,           // The minimum speed of the cars in the group.
        int maxSpeed,           // The maximum speed of the cars in the group.
        double avgSpeed)        // The average speed of the cars in the group.
{

    /**
     * Converts the current view to a {@link PriceSpeedStatisticDto} object.
     *
     * @return a DTO object containing the statistical data about price and speed.
     */
    public PriceSpeedStatisticDto toPriceSpeedStatisticDto() {
        return new PriceSpeedStatisticDto(
                minPrice, maxPrice, avgPrice, minSpeed, maxSpeed, avgSpeed
        );
    }
}
