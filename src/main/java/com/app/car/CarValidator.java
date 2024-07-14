package com.app.car;

import com.app.validate.Validator;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Car validator.
 */
@AllArgsConstructor
public class CarValidator implements Validator<Car> {

    private final String regex;
    private final int minValue;
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
     * Validate regex check string.
     *
     * @param car the car
     * @return the string
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
     * Min value validate string.
     *
     * @param car the car
     * @return the string
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
