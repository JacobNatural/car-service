package com.app.controller.dto.components;

import com.app.controller.dto.car.CarWithoutComponentsDto;

import java.util.List;

/**
 * Data Transfer Object (DTO) for representing a component with associated cars.
 * This object contains the name of the component and a list of cars that are associated with it.
 */
public record ComponentsWithCarsDto(
        /**
         * The name of the component.
         */
        String component,

        /**
         * The list of cars associated with the component, represented as {@link CarWithoutComponentsDto} objects.
         */
        List<CarWithoutComponentsDto> carDto
) {
}
