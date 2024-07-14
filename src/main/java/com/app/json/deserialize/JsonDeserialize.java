package com.app.json.deserialize;

/**
 * The interface Json deserialize.
 *
 * @param <T> the type parameter
 */
public interface JsonDeserialize<T> {
    /**
     * Deserialize t.
     *
     * @param json the json
     * @return the t
     */
    T deserialize(String json);
}
