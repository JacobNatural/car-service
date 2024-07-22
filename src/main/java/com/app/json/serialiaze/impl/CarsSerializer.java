package com.app.json.serialiaze.impl;

import com.app.json.converter.JsonConverter;
import com.app.json.serialiaze.JsonSerialize;
import com.app.json.serialiaze.generic.AbstractJsonSerializer;
import com.app.service.impl.CarServiceImpl;

/**
 * A concrete implementation of {@link AbstractJsonSerializer} for serializing {@link CarServiceImpl} objects to JSON.
 * This class extends {@link AbstractJsonSerializer} and provides the specific type {@link CarServiceImpl} for serialization.
 */
public class CarsSerializer extends AbstractJsonSerializer<CarServiceImpl> implements JsonSerialize<CarServiceImpl> {

    /**
     * Constructs a new {@link CarsSerializer} with the specified {@link JsonConverter}.
     *
     * @param jsonConverter The {@link JsonConverter} used for converting {@link CarServiceImpl} objects to JSON.
     */
    public CarsSerializer(JsonConverter<CarServiceImpl> jsonConverter) {
        super(jsonConverter);
    }
}
