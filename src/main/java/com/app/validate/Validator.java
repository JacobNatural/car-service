package com.app.validate;

import java.util.List;

/**
 * An interface defining a method for validating objects of type {@code T}.
 * <p>
 * Implementations of this interface should provide validation logic for a specific type of object.
 * If validation errors are detected, the {@link #validate(Object)} method should return a list of error messages.
 * </p>
 *
 * @param <T> the type of object to be validated.
 */
public interface Validator<T> {

    /**
     * Validates the given object of type {@code T}.
     * <p>
     * Implementations of this method should contain the validation logic for the {@code T} object and return a list of error messages
     * if any errors are found. If there are no errors, the list should be empty.
     * </p>
     *
     * @param t the object to be validated.
     * @return a list of validation error messages. The list is empty if there are no errors.
     */
    List<String> validate(T t);

    /**
     * A static helper method for validating an object and throwing an exception if validation errors occur.
     * <p>
     * This method uses the provided {@link Validator} to validate an object of type {@code T}. If the validation method returns
     * a list of errors, an {@link IllegalArgumentException} is thrown with the error messages joined together.
     * </p>
     *
     * @param <T> the type of the object to be validated.
     * @param t the object to be validated.
     * @param validator an instance of {@link Validator} used to validate the object.
     * @throws IllegalArgumentException if the list of validation errors is not empty.
     */
    static <T> void validate(T t, Validator<T> validator) {
        var errors = validator.validate(t);

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join("\n", errors));
        }
    }
}
