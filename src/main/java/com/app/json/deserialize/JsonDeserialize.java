package com.app.json.deserialize;

/**
 * An interface for deserializing objects from JSON data.
 *
 * @param <T> The type of objects to be deserialized from JSON.
 */
public interface JsonDeserialize<T> {

    /**
     * Deserializes JSON data into an object of type {@code T}.
     *
     * @param json The JSON data as a {@link String}.
     * @return An object of type {@code T} created from the JSON data.
     */
    T deserialize(String json);
}