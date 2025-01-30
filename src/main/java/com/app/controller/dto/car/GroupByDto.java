package com.app.controller.dto.car;

/**
 * Data Transfer Object (DTO) used for grouping elements by a specific parameter.
 * It contains a generic type T representing the grouping criterion,
 * along with the amount of items in each group.
 *
 * @param <T> The type of the grouping parameter (e.g., car model, color, etc.).
 */
public record GroupByDto<T>(
        /**
         * The parameter used for grouping the elements.
         * It is a generic type that can vary depending on the context (e.g., car model, color).
         */
        T paramet,

        /**
         * The amount of items in the group corresponding to the given parameter.
         */
        Long amount
) {
}
