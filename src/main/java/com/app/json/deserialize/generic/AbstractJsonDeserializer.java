package com.app.json.deserialize.generic;

import com.app.json.converter.JsonConverter;
import com.app.json.deserialize.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.FileReader;
import java.lang.reflect.ParameterizedType;

/**
 * An abstract base class for deserializing JSON data into objects of type {@code T}.
 * It uses a {@link JsonConverter} to perform the actual JSON-to-object conversion.
 *
 * @param <T> The type of object to be deserialized from JSON.
 */
@AllArgsConstructor
public abstract class AbstractJsonDeserializer<T> implements JsonDeserialize<T> {

    private final JsonConverter<T> jsonConverter;

    /**
     * The class type of the object to be deserialized.
     * This is determined using reflection to extract the generic type parameter {@code T}.
     */
    private final Class<T> tClass =
            (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    /**
     * Deserializes JSON data from a file into an object of type {@code T}.
     *
     * @param filename The name of the file containing the JSON data.
     * @return An object of type {@code T} created from the JSON data.
     * @throws IllegalArgumentException If the {@code filename} is {@code null} or empty.
     * @throws RuntimeException If an I/O error occurs while reading the file.
     */
    @Override
    @SneakyThrows
    public T deserialize(String filename) {

        if (filename == null) {
            throw new IllegalArgumentException("Filename is null");
        }
        if (filename.isEmpty()) {
            throw new IllegalArgumentException("Filename is empty");
        }

        try (var fileReader = new FileReader(filename)) {
            return jsonConverter.fromJson(fileReader, tClass);
        }
    }
}
