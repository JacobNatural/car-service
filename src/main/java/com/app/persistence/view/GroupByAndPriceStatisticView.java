package com.app.persistence.view;

import com.app.controller.dto.car.GroupByAndPriceStatisticDto;

import java.math.BigDecimal;

/**
 * A record representing a view that groups entities by a certain field and provides price statistics
 * such as the minimum and maximum price for each group.
 * <p>
 * This record is used to fetch statistical data related to a specific grouping (e.g., by brand, model, etc.)
 * along with the corresponding price statistics (min and max prices) for each group.
 * </p>
 *
 * @param <T> the type of the field used for grouping (e.g., String for brand or model).
 */
public record GroupByAndPriceStatisticView<T>(
        T t,           // The field value (e.g., brand or model) that entities are grouped by.
        BigDecimal min, // The minimum price in the group.
        BigDecimal max  // The maximum price in the group.
) {

    /**
     * Converts the current view to a {@link GroupByAndPriceStatisticDto} object.
     *
     * @return a DTO object containing the group field and the price statistics.
     */
    public GroupByAndPriceStatisticDto<Object> toGroupByAndPriceStatisticDto() {
        return new GroupByAndPriceStatisticDto<>(t, min, max);
    }
}
