package com.app.json.serialiaze.generic;

import com.app.json.converter.JsonConverter;
import com.app.json.serialiaze.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.FileWriter;

/**
 * An abstract base class for serializing objects of type {@code T} into JSON.
 * It uses a {@link JsonConverter} to perform the actual object-to-JSON conversion.
 *
 * @param <T> The type of object to be serialized to JSON.
 */
@AllArgsConstructor
public abstract class AbstractJsonSerializer<T> implements JsonSerialize<T> {

    private final JsonConverter<T> jsonConverter;

    /**
     * Serializes an object of type {@code T} into JSON and writes it to a file.
     *
     * @param data The object to be serialized.
     * @param filename The name of the file where the JSON data will be written.
     * @throws IllegalArgumentException If {@code filename} is {@code null} or empty.
     * @throws RuntimeException If an I/O error occurs while writing to the file.
     */
    @Override
    @SneakyThrows
    public void serializer(T data, String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("Filename is null");
        }
        if (filename.isEmpty()) {
            throw new IllegalArgumentException("Filename is empty");
        }
        try (FileWriter fileWriter = new FileWriter(filename)) {
            jsonConverter.toJson(data, fileWriter);
        }
    }
}
