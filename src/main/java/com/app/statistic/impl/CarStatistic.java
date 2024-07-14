package com.app.statistic.impl;

import com.app.statistic.Statistic;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The type Car statistic.
 *
 * @param <T> the type parameter
 */
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CarStatistic<T extends Comparable<T>> implements Statistic<T> {
    private final T min;
    private final T max;
    private final T average;
}
