package com.app.controller.dto.components;

import com.app.persistence.entity.ComponentEntity;

/**
 * Data Transfer Object (DTO) for creating a component.
 * This object represents the data required to create a new component.
 */
public record CreateComponentDto(
        /**
         * The name of the component.
         */
        String name
) {

    /**
     * Converts the {@link CreateComponentDto} to a {@link ComponentEntity} object.
     * This is used when saving the component to the database.
     *
     * @return A new {@link ComponentEntity} object with the name field set.
     */
    public ComponentEntity toCarComponentEntity() {
        return ComponentEntity
                .builder()
                .name(name)
                .build();
    }
}
