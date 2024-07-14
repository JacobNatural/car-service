package com.app.car;

import com.app.validate.Validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Car criterion validator.
 */
public class CarCriterionValidator implements Validator<CarCriterion> {

    @Override
    public List<String> validate(CarCriterion carCriterion) {

        var errors = new ArrayList<String>();

        if(carCriterion.brand == null){
            errors.add("Brand is null");
        }else if(carCriterion.brand.isEmpty()){
            errors.add("Brand is empty");
        }

        if(carCriterion.model == null){
            errors.add("Model is null");
        }else if(carCriterion.model.isEmpty()){
            errors.add("Model is empty");
        }

        if(carCriterion.minSpeed > carCriterion.maxSpeed){
            errors.add("Min speed greater than max speed");
        }

        if(carCriterion.minSpeed < 0){
            errors.add("Min speed less than 0");
        }

        if(carCriterion.minPrice == null || carCriterion.maxPrice == null){
            errors.add("Min price or max price is null");
        }else if(carCriterion.minPrice.compareTo(carCriterion.maxPrice) > 0){
            errors.add("Min price is greater than max price");
        }else if(carCriterion.minPrice.compareTo(BigDecimal.ZERO) < 0){
            errors.add("Min price is less than 0");
        }

        if(carCriterion.components == null){
            errors.add("Components is null");
        }else if(carCriterion.components.isEmpty()){
            errors.add("Components is empty");
        }

        return errors;
    }
}
