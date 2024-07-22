//package com.app.validate.impl;
//
//import com.app.car.Car;
//import com.app.model.Cars;
//import com.app.validate.Validator;
//import lombok.RequiredArgsConstructor;
//import java.util.ArrayList;
//import java.util.List;
//
//@RequiredArgsConstructor
//public class CarsValidator implements Validator<Cars> {
//
//    private final Validator<Car> carValidator;
//    @Override
//    public List<String> validate(Cars cars) {
//
//        var errors = new ArrayList<String>();
//
//        if(cars == null){
//            throw new IllegalArgumentException("Cars is null");
//        }
//
//        if(cars.cars() == null){
//            throw new IllegalArgumentException("List of cars is null");
//        }
//
//        if(cars.cars().isEmpty()){
//            throw new IllegalArgumentException("List of cars is empty");
//        }
//
//        for(var car : cars.cars()){
//
//            var error = carValidator.validate(car);
//
//            if(!error.isEmpty()){
//                errors.addAll(error);
//            }
//        }
//        return errors;
//    }
//
//}
package com.app.validate.impl;

import com.app.car.Car;
import com.app.model.Cars;
import com.app.validate.Validator;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Validator implementation for validating a collection of cars.
 * <p>
 * This class validates a {@link Cars} object by ensuring that each individual car within the collection
 * adheres to specific validation rules defined by a {@link Validator} for {@link Car}. It checks if the {@link Cars}
 * object and its internal list of cars are non-null and non-empty, and delegates the actual car validation
 * to the provided {@link Validator} instance.
 * </p>
 *
 * @see com.app.car.Car
 * @see com.app.model.Cars
 * @see com.app.validate.Validator
 */
@RequiredArgsConstructor
public class CarsValidator implements Validator<Cars> {

    private final Validator<Car> carValidator;

    /**
     * Validates the given {@link Cars} object.
     * <p>
     * The method performs the following checks:
     * <ul>
     *     <li>Ensures that the {@link Cars} object itself is not null.</li>
     *     <li>Ensures that the list of cars within the {@link Cars} object is not null or empty.</li>
     *     <li>Validates each individual {@link Car} object in the list using the provided {@link Validator}.</li>
     *     <li>Aggregates and returns all validation errors encountered during the process.</li>
     * </ul>
     * </p>
     *
     * @param cars the {@link Cars} object to validate.
     * @return a list of validation error messages. If there are no errors, the list will be empty.
     * @throws IllegalArgumentException if the {@link Cars} object or its list of cars is null, or if the list is empty.
     */
    @Override
    public List<String> validate(Cars cars) {

        var errors = new ArrayList<String>();

        if (cars == null) {
            throw new IllegalArgumentException("Cars is null");
        }

        if (cars.cars() == null) {
            throw new IllegalArgumentException("List of cars is null");
        }

        if (cars.cars().isEmpty()) {
            throw new IllegalArgumentException("List of cars is empty");
        }

        for (var car : cars.cars()) {

            var error = carValidator.validate(car);

            if (!error.isEmpty()) {
                errors.addAll(error);
            }
        }
        return errors;
    }
}
