package com.app.service;

import com.app.controller.dto.components.CreateComponentDto;
import com.app.persistence.entity.ComponentEntity;

import java.util.List;

/**
 * Interface ComponentService defines the operations for managing component entities.
 * It extends the CrudService interface to provide basic CRUD functionalities.
 * Additionally, it includes methods for saving component data.
 * <p>
 * This service is responsible for performing operations related to car components,
 * such as saving new components or bulk saving components.
 * </p>
 */
public interface ComponentService extends CrudService<ComponentEntity, Long> {

    /**
     * Saves a new component based on the provided data transfer object (DTO).
     *
     * @param createComponentDto the data transfer object containing the component information.
     * @return the ID of the newly saved component.
     */
    Long save(CreateComponentDto createComponentDto);

    /**
     * Saves a list of components based on the provided list of data transfer objects (DTOs).
     *
     * @param createComponentDtos the list of data transfer objects representing components.
     * @return a list of IDs of the newly saved components.
     */
    List<Long> saveAll(List<CreateComponentDto> createComponentDtos);
}
