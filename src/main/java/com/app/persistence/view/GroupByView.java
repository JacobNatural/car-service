package com.app.persistence.view;

import com.app.controller.dto.car.GroupByDto;

/**
 * A record representing a view that groups entities by a certain field and provides the count (amount) of
 * entities in each group.
 * <p>
 * This record is used to fetch data related to a specific grouping (e.g., by brand, model, etc.) and
 * the corresponding count of entities in each group.
 * </p>
 *
 * @param <T> the type of the field used for grouping (e.g., String for brand or model).
 */
public record GroupByView<T>(
        T t,          // The field value (e.g., brand or model) that entities are grouped by.
        Long amount   // The count of entities in the group.
) {

    /**
     * Converts the current view to a {@link GroupByDto} object.
     *
     * @return a DTO object containing the group field and the count of entities.
     */
    public GroupByDto<Object> toGroupByDto() {
        return new GroupByDto<>(t, amount);
    }
}
