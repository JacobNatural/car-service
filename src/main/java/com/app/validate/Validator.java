package com.app.validate;

import java.util.List;

/**
 * The interface Validator.
 *
 * @param <T> the type parameter
 */
public interface Validator<T> {
    /**
     * Validate list.
     *
     * @param t the t
     * @return the list
     */
    List<String> validate(T t);

    /**
     * Validate.
     *
     * @param <T>       the type parameter
     * @param t         the t
     * @param validator the validator
     */
    static<T> void validate(T t, Validator<T> validator){

        var errors = validator.validate(t);

        if(!errors.isEmpty()){
            throw new IllegalArgumentException(String.join("\n",errors));
        }
    }

}
