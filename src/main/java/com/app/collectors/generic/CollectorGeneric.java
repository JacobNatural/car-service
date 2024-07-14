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
 * The type Collector generic.
 *
 * @param <T> the type parameter
 * @param <U> the type parameter
 * @param <W> the type parameter
 */
@RequiredArgsConstructor
public abstract class CollectorGeneric<T,U,W> implements Collector<T, List<U>, W> {

    private final Function<T, U> mapper;

    @Override
    public BiConsumer<List<U>, T> accumulator() {
        return (cars, car) -> cars.add(mapper.apply(car));
    }


    @Override
    public Supplier<List<U>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BinaryOperator<List<U>> combiner() {
        return (l1, l2) ->{
            l1.addAll(l2);
            return l1;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.CONCURRENT);
    }
}
