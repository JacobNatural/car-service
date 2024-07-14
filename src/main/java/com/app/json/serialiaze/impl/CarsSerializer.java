package com.app.json.serialiaze.impl;

import com.app.json.converter.JsonConverter;
import com.app.json.serialiaze.JsonSerialize;
import com.app.json.serialiaze.generic.AbstractJsonSerializer;
import com.app.service.impl.CarServiceImpl;

/**
 * The type Cars serializer.
 */
public class CarsSerializer extends AbstractJsonSerializer<CarServiceImpl> implements JsonSerialize<CarServiceImpl> {
    /**
     * Instantiates a new Cars serializer.
     *
     * @param jsonConverter the json converter
     */
    public CarsSerializer(JsonConverter<CarServiceImpl> jsonConverter) {
        super(jsonConverter);
    }
}
