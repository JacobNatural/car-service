package com.app.statistic.impl;

import com.app.statistic.Statistic;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Represents statistical information about the minimum, maximum, and average values of a certain type.
 * <p>
 * This class is used to store and provide access to the minimum, maximum, and average values of a specific type
 * that implements {@link Comparable}. It is typically used in scenarios where such comprehensive statistics are needed,
 * such as in financial reporting, data analysis, or performance metrics.
 * </p>
 *
 * @param <T> the type of the values, which must extend {@link Comparable}
 */
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CarStatistic<T extends Comparable<T>> implements Statistic<T> {

    /**
     * The minimum value.
     */
    private final T min;

    /**
     * The maximum value.
     */
    private final T max;

    /**
     * The average value.
     */
    private final T average;
}