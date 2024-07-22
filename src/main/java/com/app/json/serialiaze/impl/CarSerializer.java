package com.app.json.serialiaze.impl;

import com.app.car.Car;
import com.app.json.converter.JsonConverter;
import com.app.json.serialiaze.JsonSerialize;
import com.app.json.serialiaze.generic.AbstractJsonSerializer;

/**
 * A concrete implementation of {@link AbstractJsonSerializer} for serializing {@link Car} objects to JSON.
 * This class extends {@link AbstractJsonSerializer} and provides the specific type {@link Car} for serialization.
 */
public class CarSerializer extends AbstractJsonSerializer<Car> implements JsonSerialize<Car> {

    /**
     * Constructs a new {@link CarSerializer} with the specified {@link JsonConverter}.
     *
     * @param jsonConverter The {@link JsonConverter} used for converting {@link Car} objects to JSON.
     */
    public CarSerializer(JsonConverter<Car> jsonConverter) {
        super(jsonConverter);
    }
}