package com.app.json.deserialize.impl;

import com.app.json.converter.JsonConverter;
import com.app.json.deserialize.JsonDeserialize;
import com.app.json.deserialize.generic.AbstractJsonDeserializer;
import com.app.model.Cars;

/**
 * A concrete implementation of {@link AbstractJsonDeserializer} for deserializing {@link Cars} objects from JSON.
 * This class extends {@link AbstractJsonDeserializer} and provides the specific type {@link Cars} for deserialization.
 */
public class CarsDeserializer extends AbstractJsonDeserializer<Cars> implements JsonDeserialize<Cars> {

    /**
     * Constructs a new {@link CarsDeserializer} with the specified {@link JsonConverter}.
     *
     * @param jsonConverter The {@link JsonConverter} used for converting JSON to {@link Cars} objects.
     */
    public CarsDeserializer(JsonConverter<Cars> jsonConverter) {
        super(jsonConverter);
    }
}