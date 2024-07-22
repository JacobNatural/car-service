package com.app.car;

import com.app.validate.Validator;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Validator for {@link Car} objects.
 * This class validates a {@link Car} instance based on regex patterns for the brand, model, and components,
 * as well as minimum value constraints for speed and price.
 */
@AllArgsConstructor
public class CarValidator implements Validator<Car> {

    /**
     * The regex pattern used to validate the {@link Car}'s brand, model, and components.
     */
    private final String regex;

    /**
     * The minimum value for speed and price that a {@link Car} must meet or exceed.
     */
    private final int minValue;

    /**
     * Validates the provided {@link Car} instance based on regex patterns and minimum value constraints.
     *
     * @param car The {@link Car} instance to validate.
     * @return A list of error messages. The list is empty if the {@link Car} instance is valid. Each error message
     *         provides details about why the instance failed validation.
     * @throws IllegalArgumentException If the provided {@link Car} instance is null.
     */
    @Override
    public List<String> validate(Car car) {

        if(car == null){
            throw new IllegalArgumentException("Car is null");
        }

        var list = new ArrayList<String>(List.of());

        var minVal = minValueValidate( car);
        var carRegex = validateRegexCheck( car);

        if(carRegex != null){
            list.add(carRegex);
        }
        if(minVal != null){
            list.add(minVal);
        }

        return list;
    }

    /**
     * Checks if the brand, model, and components of the {@link Car} match the regex pattern.
     *
     * @param car The {@link Car} instance to validate.
     * @return An error message if any attribute does not match the regex pattern; {@code null} if all attributes are valid.
     */
    private String validateRegexCheck( Car car) {
        if (!car.brand.matches(regex)) {
            return STR."Brand \{car.brand} does not match regex";
        }
        if (!car.model.matches(regex)) {
            return STR."Model \{car.model} does not match regex";
        }
        for (var comp : car.components) {
            if (!comp.matches(regex)) {
                return STR."Component \{comp} does not match regex";
            }
        }
        return null;
    }

    /**
     * Checks if the speed and price of the {@link Car} meet the minimum value constraints.
     *
     * @param car The {@link Car} instance to validate.
     * @return An error message if speed or price are less than the minimum value; {@code null} if all values are valid.
     */
    private String minValueValidate( Car car) {

        if (car.speed < minValue) {
            return STR."Speed \{car.speed} can't be less than \{minValue}";
        }

        if (car.price.compareTo(BigDecimal.valueOf(minValue)) < 0) {
            return STR."Price \{car.price} can't be less than \{minValue}";
        }

        return null;
    }


}
