package com.app.collectors;

import com.app.car.Car;
import com.app.collectors.generic.CollectorGeneric;
import com.app.statistic.Statistic;
import com.app.statistic.impl.CarMinMax;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;

/**
 * A {@link Collector} implementation that collects {@link Car} objects and computes statistical values (minimum and maximum)
 * of a specified property of the cars.
 *
 * The type of the property being collected (in this case, {@link BigDecimal}).
 */
public class CarMinMaxCollector extends CollectorGeneric<Car, BigDecimal, Statistic<BigDecimal>> {

    /**
     * Constructs a new {@link CarMinMaxCollector}.
     *
     * @param mapper A function that extracts a {@link BigDecimal} property from a {@link Car} object.
     */
    public CarMinMaxCollector(Function<Car, BigDecimal> mapper) {
        super(mapper);
    }

    /**
     * Returns a {@link Function} that processes the list of {@link BigDecimal} values collected from the {@link Car} objects,
     * and computes the minimum and maximum values, encapsulated in a {@link Statistic}.
     *
     * @return A {@link Function} that transforms a list of {@link BigDecimal} into a {@link Statistic} containing the minimum
     *         and maximum values.
     */
    @Override
    public Function<List<BigDecimal>, Statistic<BigDecimal>> finisher() {

        return prices -> {
            var min = prices
                    .stream()
                    .min(Comparator.naturalOrder())
                    .orElse(BigDecimal.ZERO);
            var max = prices
                    .stream()
                    .max(Comparator.naturalOrder())
                    .orElse(BigDecimal.ZERO);
            return new CarMinMax<>(min, max);
        };
    }
}