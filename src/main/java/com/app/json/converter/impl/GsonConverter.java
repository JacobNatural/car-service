package com.app.json.converter.impl;

import com.app.json.converter.JsonConverter;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;

import java.io.FileReader;
import java.io.FileWriter;

/**
 * An implementation of {@link JsonConverter} that uses the Gson library to convert objects to and from JSON format.
 *
 * @param <T> The type of objects to be converted to and from JSON.
 */
@AllArgsConstructor
public class GsonConverter<T> implements JsonConverter<T> {

    private final Gson gson;

    /**
     * Converts the given object to JSON and writes it to the provided {@link FileWriter}.
     *
     * @param data The object to convert to JSON.
     * @param fileWriter The {@link FileWriter} to which the JSON representation should be written.
     * @throws IllegalArgumentException If either {@code data} or {@code fileWriter} is {@code null}.
     */
    @Override
    public void toJson(T data, FileWriter fileWriter) {
        if(data == null){
            throw new IllegalArgumentException("Data is null");
        }
        if(fileWriter == null){
            throw new IllegalArgumentException("FileWriter is null");
        }

        gson.toJson(data, fileWriter);
    }

    /**
     * Converts the JSON from the provided {@link FileReader} into an object of the specified class.
     *
     * @param fileReader The {@link FileReader} from which to read the JSON.
     * @param tClass The {@link Class} of the object to create from the JSON.
     * @return The object created from the JSON.
     * @throws IllegalArgumentException If either {@code fileReader} or {@code tClass} is {@code null}.
     */
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