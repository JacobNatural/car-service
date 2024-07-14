package com.app.json.converter.impl;

import com.app.json.converter.JsonConverter;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * The type Gson converter.
 *
 * @param <T> the type parameter
 */
@AllArgsConstructor
public class GsonConverter<T> implements JsonConverter<T> {

    private final Gson gson;

    @Override
    public void toJson(T data, FileWriter fileWriter) {
        if(data == null){
            throw new IllegalArgumentException("Data is null");
        }
        if(fileWriter == null){
            throw new IllegalArgumentException("FileWriter is null");
        }

        gson.toJson(data,fileWriter);
    }

    @Override
    public T fromJson(FileReader fileReader, Class<T> tClass) {
        if(fileReader == null){
            throw new IllegalArgumentException("FileReader is null");
        }
        if(tClass == null){
            throw new IllegalArgumentException("tClass is null");
        }
        return gson.fromJson(fileReader, tClass);
    }
}
