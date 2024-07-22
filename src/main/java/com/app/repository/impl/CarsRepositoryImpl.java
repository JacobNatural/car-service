package com.app.repository.impl;

import com.app.car.Car;
import com.app.repository.Repository;
import com.app.json.deserialize.JsonDeserialize;
import com.app.model.Cars;
import com.app.validate.Validator;
import java.util.List;

/**
 * Implementation of {@link Repository} for managing {@link Car} objects.
 * <p>
 * This class provides access to a collection of {@link Car} objects by loading them from a JSON file.
 * The data is validated using a {@link Validator} before being stored.
 * </p>
 */
public class CarsRepositoryImpl implements Repository<Car> {

    private final List<Car> cars;

    /**
     * Constructs a new {@link CarsRepositoryImpl} with the given parameters.
     * <p>
     * The constructor initializes the repository by deserializing {@link Cars} from the specified JSON file
     * and validates the data using the provided {@link Validator}.
     * </p>
     *
     * @param filename       the path to the JSON file containing the car data
     * @param validator      the {@link Validator} used to validate the deserialized {@link Cars}
     * @param jsonDeserialize the {@link JsonDeserialize} implementation used for deserializing JSON data
     * @throws IllegalArgumentException if the data is invalid according to the provided validator
     */
    public CarsRepositoryImpl(String filename, Validator<Cars> validator, JsonDeserialize<Cars> jsonDeserialize) {

        var cars = jsonDeserialize.deserialize(filename);
        Validator.validate(cars, validator);

        this.cars = cars.cars();
    }

    /**
     * Returns all {@link Car} objects managed by this repository.
     *
     * @return a {@link List} of {@link Car} objects
     */
    @Override
    public List<Car> getAll() {
        return cars;
    }
}
