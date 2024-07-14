package com.app.statistic.impl;

import com.app.statistic.Statistic;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The type Car min max.
 *
 * @param <T> the type parameter
 */
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CarMinMax<T extends Comparable<T>> implements Statistic<T> {
    private final T minPrice;
    private final T maxPrice;
}
