package com.app.json.serialiaze.impl;

import com.app.car.Car;
import com.app.json.converter.JsonConverter;
import com.app.json.serialiaze.JsonSerialize;
import com.app.json.serialiaze.generic.AbstractJsonSerializer;

/**
 * The type Car serializer.
 */
public class CarSerializer extends AbstractJsonSerializer<Car> implements JsonSerialize<Car> {
    /**
     * Instantiates a new Car serializer.
     *
     * @param jsonConverter the json converter
     */
    public CarSerializer(JsonConverter<Car> jsonConverter) {
        super(jsonConverter);
    }
}
