package com.app.json.serialiaze;

/**
 * The interface Json serialize.
 *
 * @param <T> the type parameter
 */
public interface JsonSerialize <T>{
    /**
     * Serializer.
     *
     * @param data     the data
     * @param filename the filename
     */
    void serializer(T data, String filename);
}
