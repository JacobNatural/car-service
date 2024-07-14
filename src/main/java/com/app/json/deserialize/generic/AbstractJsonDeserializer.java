package com.app.json.deserialize.generic;

import com.app.json.converter.JsonConverter;
import com.app.json.deserialize.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import java.io.FileReader;
import java.lang.reflect.ParameterizedType;

/**
 * The type Abstract json deserializer.
 *
 * @param <T> the type parameter
 */
@AllArgsConstructor
public abstract class AbstractJsonDeserializer<T> implements JsonDeserialize<T> {

    private final JsonConverter<T> jsonConverter;
    private final Class<T> tClass =
            (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @Override
    @SneakyThrows
    public T deserialize(String filename) {

        if(filename == null){
            throw new IllegalArgumentException("Filename is null");
        }
        if(filename.isEmpty()){
            throw new IllegalArgumentException("Filename is empty");
        }


        try(var fileReader = new FileReader(filename)) {
            return jsonConverter.fromJson(fileReader,tClass);
        }
    }
}
