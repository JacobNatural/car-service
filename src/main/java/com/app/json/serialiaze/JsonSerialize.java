package com.app.json.serialiaze;

/**
 * Interface for serializing objects to JSON format.
 *
 * @param <T> the type of the object to be serialized
 */
public interface JsonSerialize<T> {

    /**
     * Serializes the given data object to a JSON file.
     *
     * @param data     the object to be serialized
     * @param filename the name of the file to write the JSON data to
     * @throws IllegalArgumentException if the filename is null or empty
     */
    void serializer(T data, String filename);
}