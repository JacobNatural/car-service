package com.app.statistic.impl;

import com.app.statistic.Statistic;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Represents statistical information about the minimum and maximum values of a certain type.
 * <p>
 * This class is used to store and provide access to the minimum and maximum values of a specific type
 * that implements {@link Comparable}. It is typically used in scenarios where such statistics are needed,
 * such as in financial calculations or data analysis.
 * </p>
 *
 * @param <T> the type of the values, which must extend {@link Comparable}
 */
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CarMinMax<T extends Comparable<T>> implements Statistic<T> {

    /**
     * The minimum value.
     */
    private final T minPrice;

    /**
     * The maximum value.
     */
    private final T maxPrice;
}