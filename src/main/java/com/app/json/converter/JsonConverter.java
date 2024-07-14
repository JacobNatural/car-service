package com.app.json.converter;

import java.io.FileReader;
import java.io.FileWriter;

/**
 * The interface Json converter.
 *
 * @param <T> the type parameter
 */
public interface JsonConverter <T>{
    /**
     * To json.
     *
     * @param data       the data
     * @param fileWriter the file writer
     */
    void toJson(T data, FileWriter fileWriter);

    /**
     * From json t.
     *
     * @param fileReader the file reader
     * @param tClass     the t class
     * @return the t
     */
    T fromJson(FileReader fileReader, Class<T> tClass);
}
