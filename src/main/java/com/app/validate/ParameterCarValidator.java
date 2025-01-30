package com.app.validate;

import com.app.persistence.entity.CarEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * {@code ParameterCarValidator} is a component responsible for validating the given parameter name.
 * It ensures that the provided parameter is not null, not empty, and matches a valid field name of the {@link CarEntity} class or its superclass.
 */
@Component
public class ParameterCarValidator implements Validator<String> {

    /**
     * Validates the provided parameter name.
     * The following validations are performed:
     * <ul>
     *     <li>The parameter cannot be null.</li>
     *     <li>The parameter cannot be empty.</li>
     *     <li>The parameter must match one of the field names of {@link CarEntity} or its superclass.</li>
     * </ul>
     *
     * @param s the parameter name to be validated
     * @throws IllegalArgumentException if any validation check fails
     */
    @Override
    public void validate(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Parameter cannot be null");
        }

        if (s.isEmpty()) {
            throw new IllegalArgumentException("Parameter cannot be empty");
        }

        var arguments = Arrays
                .stream(CarEntity.class.getDeclaredFields())
                .map(arg -> arg.getName())
                .toList();

        var superClassArguments = Arrays
                .stream(CarEntity.class.getSuperclass().getDeclaredFields())
                .map(arg -> arg.getName())
                .toList();

        if (!arguments.contains(s) && !superClassArguments.contains(s)) {
            throw new IllegalArgumentException("Invalid parameter value: " + s);
        }
    }
}
