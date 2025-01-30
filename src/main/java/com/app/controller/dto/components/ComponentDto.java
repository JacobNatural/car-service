package com.app.controller.dto.components;

/**
 * Data Transfer Object (DTO) for representing a component.
 * This object contains the component's ID and name.
 */
public record ComponentDto(
        /**
         * The unique identifier of the component.
         */
        Long id,

        /**
         * The name of the component.
         */
        String name
) {
}
