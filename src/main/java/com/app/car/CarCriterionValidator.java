package com.app.car;

import com.app.validate.Validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Validator for {@link CarCriterion} objects.
 * This class checks the validity of a {@link CarCriterion} instance based on several criteria, such as
 * non-null and non-empty brand and model, valid speed and price ranges, and non-null and non-empty components list.
 */
public class CarCriterionValidator implements Validator<CarCriterion> {

    /**
     * Validates the provided {@link CarCriterion} instance.
     *
     * @param carCriterion The {@link CarCriterion} instance to validate.
     * @return A list of error messages. The list is empty if the instance is valid. Each error message provides
     *         details about why the instance failed validation.
     */
    @Override
    public List<String> validate(CarCriterion carCriterion) {

        var errors = new ArrayList<String>();

        // Check if brand is null or empty
        if (carCriterion.brand == null) {
            errors.add("Brand is null");
        } else if (carCriterion.brand.isEmpty()) {
            errors.add("Brand is empty");
        }

        // Check if model is null or empty
        if (carCriterion.model == null) {
            errors.add("Model is null");
        } else if (carCriterion.model.isEmpty()) {
            errors.add("Model is empty");
        }

        // Check if minSpeed is greater than maxSpeed
        if (carCriterion.minSpeed > carCriterion.maxSpeed) {
            errors.add("Min speed greater than max speed");
        }

        // Check if minSpeed is less than 0
        if (carCriterion.minSpeed < 0) {
            errors.add("Min speed less than 0");
        }

        // Check if minPrice and maxPrice are valid
        if (carCriterion.minPrice == null || carCriterion.maxPrice == null) {
            errors.add("Min price or max price is null");
        } else if (carCriterion.minPrice.compareTo(carCriterion.maxPrice) > 0) {
            errors.add("Min price is greater than max price");
        } else if (carCriterion.minPrice.compareTo(BigDecimal.ZERO) < 0) {
            errors.add("Min price is less than 0");
        }

        // Check if components is null or empty
        if (carCriterion.components == null) {
            errors.add("Components is null");
        } else if (carCriterion.components.isEmpty()) {
            errors.add("Components is empty");
        }

        return errors;
    }
}