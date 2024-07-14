package com.app.repository.impl;

import com.app.car.Car;
import com.app.repository.Repository;
import com.app.json.deserialize.JsonDeserialize;
import com.app.model.Cars;
import com.app.validate.Validator;
import lombok.ToString;

import java.util.List;


/**
 * The type Cars repository.
 */

public class CarsRepositoryImpl implements Repository<Car> {

    private final List<Car> cars;

    /**
     * Instantiates a new Cars repository.
     *
     * @param filename        the filename
     * @param validator       the validator
     * @param jsonDeserialize the json deserialize
     */
    public CarsRepositoryImpl(String filename, Validator<Cars> validator, JsonDeserialize<Cars> jsonDeserialize) {

        var cars = jsonDeserialize.deserialize(filename);
        Validator.validate(cars, validator);

        this.cars = cars.cars();
    }

    @Override
    public List<Car> getAll() {
        return cars;
    }
}
