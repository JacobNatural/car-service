package com.app.statistic;

/**
 * Represents a generic statistical measure.
 * <p>
 * This interface serves as a marker for statistical data types. It provides a common interface for various statistical
 * measures that can be implemented to represent different types of statistics, such as minimum, maximum, average,
 * and other statistical aggregates.
 * </p>
 *
 * @param <T> the type of the data used in the statistical measure, which must extend {@link Comparable}
 */
public interface Statistic<T> {
}