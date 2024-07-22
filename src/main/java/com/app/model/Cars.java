package com.app.model;

import com.app.car.Car;
import java.util.List;

/**
 * Represents a collection of {@link Car} objects.
 *
 * This record encapsulates a list of {@link Car} objects, providing a way to manage and manipulate a collection of cars.
 *
 * @param cars the list of {@link Car} objects
 */
public record Cars(List<Car> cars)  { }