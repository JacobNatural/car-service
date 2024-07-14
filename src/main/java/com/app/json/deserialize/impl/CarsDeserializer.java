package com.app.json.deserialize.impl;
import com.app.json.converter.JsonConverter;
import com.app.json.deserialize.JsonDeserialize;
import com.app.json.deserialize.generic.AbstractJsonDeserializer;
import com.app.model.Cars;

public class CarsDeserializer extends AbstractJsonDeserializer<Cars> implements JsonDeserialize<Cars> {
    public CarsDeserializer(JsonConverter<Cars> jsonConverter) {
        super(jsonConverter);
    }
}
