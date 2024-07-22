package com.app.collectors.generic;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * A generic implementation of the {@link Collector} interface.
 * This abstract class provides a basic implementation of a {@link Collector} that collects elements of type {@code T}
 * into a list of type {@code U} and then produces a result of type {@code W}.
 *
 * @param <T> The type of input elements to the collector.
 * @param <U> The type of elements in the intermediate list.
 * @param <W> The type of the result produced by the collector.
 */
@RequiredArgsConstructor
public abstract class CollectorGeneric<T, U, W> implements Collector<T, List<U>, W> {

    /**
     * A function that maps elements of type {@code T} to elements of type {@code U}.
     */
    private final Function<T, U> mapper;

    /**
     * Returns a {@link BiConsumer} that accumulates input elements into a list of type {@code U}.
     * The list is created using the supplier and elements are added to it by applying the mapper function.
     *
     * @return A {@link BiConsumer} that accumulates input elements into a list.
     */
    @Override
    public BiConsumer<List<U>, T> accumulator() {
        return (cars, car) -> cars.add(mapper.apply(car));
    }

    /**
     * Returns a {@link Supplier} that provides a new empty list of type {@code U}.
     *
     * @return A {@link Supplier} that provides a new empty list.
     */
    @Override
    public Supplier<List<U>> supplier() {
        return ArrayList::new;
    }

    /**
     * Returns a {@link BinaryOperator} that combines two lists of type {@code U} by adding all elements of the second
     * list to the first list.
     *
     * @return A {@link BinaryOperator} that combines two lists.
     */
    @Override
    public BinaryOperator<List<U>> combiner() {
        return (l1, l2) -> {
            l1.addAll(l2);
            return l1;
        };
    }

    /**
     * Returns a set of characteristics that describe the behavior of the collector.
     * This implementation returns a set containing {@link Characteristics#CONCURRENT}.
     *
     * @return A set of characteristics describing the behavior of the collector.
     */
    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.CONCURRENT);
    }
}
