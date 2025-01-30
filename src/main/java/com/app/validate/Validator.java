package com.app.validate;

/**
 * The {@code Validator} interface defines a contract for classes that perform validation on a given object.
 * It provides a method to validate the object of type {@code T}.
 * Implementations of this interface should define the specific validation logic for the given type.
 *
 * @param <T> the type of object to be validated
 */
public interface Validator<T> {

    /**
     * Validates the given object.
     * The implementation should perform the necessary checks to ensure the object is valid.
     * If the object is invalid, an appropriate exception should be thrown.
     *
     * @param t the object to be validated
     * @throws IllegalArgumentException if the object is not valid
     */
    void validate(T t);
}
