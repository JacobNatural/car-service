package com.app.json.serialiaze.generic;

import com.app.json.converter.JsonConverter;
import com.app.json.serialiaze.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import java.io.FileWriter;

/**
 * The type Abstract json serializer.
 *
 * @param <T> the type parameter
 */
@AllArgsConstructor
public abstract class AbstractJsonSerializer <T>  implements JsonSerialize<T> {

    private final JsonConverter<T> jsonConverter;

    @Override
    @SneakyThrows
    public void serializer(T data, String filename) {
        if(filename == null){
            throw new IllegalArgumentException("Filename is null");
        }
        if(filename.isEmpty()){
            throw new IllegalArgumentException("Filename is empty");
        }
        try (FileWriter fileWriter = new FileWriter(filename)){
            jsonConverter.toJson(data, fileWriter);
        }
    }
}
