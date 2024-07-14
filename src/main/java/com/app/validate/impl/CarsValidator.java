    package com.app.validate.impl;

import com.app.car.Car;
import com.app.model.Cars;
import com.app.validate.Validator;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Cars validator.
 */
@RequiredArgsConstructor
public class CarsValidator implements Validator<Cars> {

    private final Validator<Car> carValidator;
    @Override
    public List<String> validate(Cars cars) {

        var errors = new ArrayList<String>();

        if(cars == null){
            throw new IllegalArgumentException("Cars is null");
        }

        if(cars.cars() == null){
            throw new IllegalArgumentException("List of cars is null");
        }

        if(cars.cars().isEmpty()){
            throw new IllegalArgumentException("List of cars is empty");
        }

        for(var car : cars.cars()){

            var error = carValidator.validate(car);

            if(!error.isEmpty()){
                errors.addAll(error);
            }
        }
        return errors;
    }

}
