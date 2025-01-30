package com.app.service.impl;

import com.app.controller.dto.components.CreateComponentDto;
import com.app.persistence.ComponentRepository;
import com.app.persistence.CrudRepository;
import com.app.persistence.entity.ComponentEntity;
import com.app.service.ComponentService;
import com.app.service.impl.generic.CrudServiceGeneric;
import com.app.validate.CreateComponentDtoValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The ComponentServiceImpl class implements the ComponentService interface and provides
 * functionality for managing car components. This includes saving individual components
 * and saving multiple components at once.
 * <p>
 * The class interacts with the ComponentRepository to perform CRUD operations on
 * component entities. It also uses a validator to ensure that the data for each
 * component is valid before it is persisted in the database.
 * </p>
 */
@Service
@Transactional
public class ComponentServiceImpl extends CrudServiceGeneric<ComponentEntity, Long> implements ComponentService {

    private final ComponentRepository componentRepository;
    private final CreateComponentDtoValidator createComponentDtoValidator;

    /**
     * Constructor for ComponentServiceImpl.
     *
     * @param crudRepository            the repository for generic CRUD operations.
     * @param componentRepository       the repository for component entities.
     * @param createComponentDtoValidator the validator for CreateComponentDto objects.
     */
    public ComponentServiceImpl(
            CrudRepository<ComponentEntity, Long> crudRepository,
            ComponentRepository componentRepository,
            CreateComponentDtoValidator createComponentDtoValidator
    ) {
        super(crudRepository);
        this.componentRepository = componentRepository;
        this.createComponentDtoValidator = createComponentDtoValidator;
    }

    /**
     * Saves a new component entity based on the provided data transfer object (DTO).
     *
     * @param createComponentDto the data transfer object containing component information.
     * @return the ID of the newly saved component.
     * @throws IllegalArgumentException if the DTO is invalid.
     */
    @Override
    public Long save(CreateComponentDto createComponentDto) {
        createComponentDtoValidator.validate(createComponentDto);
        return componentRepository
                .save(createComponentDto.toCarComponentEntity())
                .getId();
    }

    /**
     * Saves a list of component entities based on the provided list of data transfer objects (DTOs).
     *
     * @param createComponentDtos the list of data transfer objects representing components.
     * @return a list of IDs of the newly saved components.
     * @throws IllegalArgumentException if any of the DTOs are invalid.
     */
    @Override
    public List<Long> saveAll(List<CreateComponentDto> createComponentDtos) {
        return componentRepository
                .saveAll(createComponentDtos.stream()
                        .peek(createComponentDtoValidator::validate)
                        .map(CreateComponentDto::toCarComponentEntity)
                        .toList())
                .stream()
                .map(ComponentEntity::getId)
                .toList();
    }
}
