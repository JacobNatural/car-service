package com.app.json.converter;

import java.io.FileReader;
import java.io.FileWriter;

/**
 * An interface for converting objects to and from JSON format.
 *
 * @param <T> The type of objects to be converted to and from JSON.
 */
public interface JsonConverter<T> {

    /**
     * Converts the specified object to JSON and writes it to the provided {@link FileWriter}.
     *
     * @param data The object to convert to JSON.
     * @param fileWriter The {@link FileWriter} to which the JSON representation should be written.
     */
    void toJson(T data, FileWriter fileWriter);

    /**
     * Converts the JSON from the provided {@link FileReader} into an object of the specified class.
     *
     * @param fileReader The {@link FileReader} from which to read the JSON.
     * @param tClass The {@link Class} of the object to create from the JSON.
     * @return The object created from the JSON.
     */
    T fromJson(FileReader fileReader, Class<T> tClass);
}