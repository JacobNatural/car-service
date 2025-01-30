package com.app.validate;

import com.app.controller.dto.car.CreateCarDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * {@code CreateCarDtoValidator} is a component responsible for validating the data of a {@link CreateCarDto}.
 * It ensures that the provided {@link CreateCarDto} object meets various constraints, including
 * non-null and non-empty values, matching patterns for the car's model and brand, as well as
 * enforcing minimum speed and price limits.
 */
@Component
public class CreateCarDtoValidator implements Validator<CreateCarDto> {

    private final String modelRegex;
    private final String brandRegex;
    private final int minimumSpeed;
    private final BigDecimal minimumPrice;

    /**
     * Constructs a new {@code CreateCarDtoValidator} with the specified validation parameters.
     *
     * @param minimumPrice the minimum price a car can have
     * @param minimumSpeed the minimum speed a car can have
     * @param brandRegex the regex pattern for validating the car brand
     * @param modelRegex the regex pattern for validating the car model
     */
    public CreateCarDtoValidator(
            @Value("${validate.car.min.price}") BigDecimal minimumPrice,
            @Value("${validate.car.min.speed}") int minimumSpeed,
            @Value("${validate.car.regex.brand}") String brandRegex,
            @Value("${validate.car.regex.model}") String modelRegex) {
        this.minimumPrice = minimumPrice;
        this.minimumSpeed = minimumSpeed;
        this.brandRegex = brandRegex;
        this.modelRegex = modelRegex;
    }

    /**
     * Validates the provided {@link CreateCarDto}.
     * The following validations are performed:
     * <ul>
     *     <li>The model cannot be null, empty, or invalid according to the {@code modelRegex}.</li>
     *     <li>The brand cannot be null, empty, or invalid according to the {@code brandRegex}.</li>
     *     <li>The speed cannot be lower than the configured minimum speed.</li>
     *     <li>The price cannot be null or lower than the configured minimum price.</li>
     *     <li>The components cannot be null or empty.</li>
     * </ul>
     *
     * @param createCarDto the {@link CreateCarDto} to be validated
     * @throws IllegalArgumentException if any validation check fails
     */
    @Override
    public void validate(CreateCarDto createCarDto) {

        if (createCarDto.model() == null) {
            throw new IllegalArgumentException("Model cannot be null.");
        }

        if (createCarDto.model().isEmpty()) {
            throw new IllegalArgumentException("Model cannot be empty.");
        }

        if (!createCarDto.model().matches(modelRegex)) {
            throw new IllegalArgumentException("The model does not match the pattern: " + modelRegex + ".");
        }

        if (createCarDto.brand() == null) {
            throw new IllegalArgumentException("Brand cannot be null.");
        }

        if (createCarDto.brand().isEmpty()) {
            throw new IllegalArgumentException("Brand cannot be empty.");
        }

        if (!createCarDto.brand().matches(brandRegex)) {
            throw new IllegalArgumentException("The brand does not match the pattern: " + brandRegex + ".");
        }

        if (createCarDto.speed() < minimumSpeed) {
            throw new IllegalArgumentException("The speed does not match the minimum speed: " + minimumSpeed + ".");
        }

        if (createCarDto.price() == null) {
            throw new IllegalArgumentException("Price cannot be null.");
        }

        if (createCarDto.price().compareTo(minimumPrice) < 0) {
            throw new IllegalArgumentException("The price does not match the minimum price: " + minimumPrice + ".");
        }

        if (createCarDto.components() == null) {
            throw new IllegalArgumentException("Components cannot be null.");
        }

        if (createCarDto.components().isEmpty()) {
            throw new IllegalArgumentException("Components cannot be empty.");
        }
    }
}
