package com.app.controller.dto.car;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) used for grouping and calculating price statistics.
 * It contains a generic type T, which represents a grouping criteria,
 * along with the minimum and maximum price for the grouped items.
 *
 * @param <T> The type of the grouping criterion (e.g., car model, color, etc.).
 */
public record GroupByAndPriceStatisticDto<T>(
        /**
         * The grouping criterion (e.g., car model, color).
         * It is a generic type that can vary depending on the context.
         */
        T t,

        /**
         * The minimum price in the group.
         */
        BigDecimal min,

        /**
         * The maximum price in the group.
         */
        BigDecimal max
) {
}
