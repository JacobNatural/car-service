package com.app.validate;

import com.app.controller.dto.components.CreateComponentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * {@code CreateComponentDtoValidator} is a component responsible for validating the data of a {@link CreateComponentDto}.
 * It ensures that the component's name is non-null, non-empty, and matches a specific regex pattern.
 */
@Component
public class CreateComponentDtoValidator implements Validator<CreateComponentDto> {

    private final String nameRegex;

    /**
     * Constructs a new {@code CreateComponentDtoValidator} with the specified name validation regex.
     *
     * @param nameRegex the regex pattern for validating the component's name
     */
    public CreateComponentDtoValidator(@Value("${validate.components.regex.name}") String nameRegex) {
        this.nameRegex = nameRegex;
    }

    /**
     * Validates the provided {@link CreateComponentDto}.
     * The following validations are performed:
     * <ul>
     *     <li>The name cannot be null or empty.</li>
     *     <li>The name must match the specified regex pattern.</li>
     * </ul>
     *
     * @param createComponentDto the {@link CreateComponentDto} to be validated
     * @throws IllegalArgumentException if any validation check fails
     */
    @Override
    public void validate(CreateComponentDto createComponentDto) {
        if (createComponentDto.name() == null) {
            throw new IllegalArgumentException("Component name cannot be null.");
        }

        if(createComponentDto.name().isEmpty()){
            throw new IllegalArgumentException("Component name cannot be empty.");
        }

        if(!createComponentDto.name().matches(nameRegex)){
            throw new IllegalArgumentException("The name does not match the pattern: " + nameRegex + ".");
        }
    }
}
