package com.app.collectors;

import com.app.car.Car;
import com.app.collectors.generic.CollectorGeneric;
import com.app.statistic.Statistic;
import com.app.statistic.impl.CarMinMax;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * The type Car min max collector.
 */
public class CarMinMaxCollector extends CollectorGeneric<Car,BigDecimal, Statistic<BigDecimal>> {

    /**
     * Instantiates a new Car min max collector.
     *
     * @param mapper the mapper
     */
    public CarMinMaxCollector(Function<Car, BigDecimal> mapper) {
        super(mapper);
    }

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
