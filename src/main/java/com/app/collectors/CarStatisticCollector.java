package com.app.collectors;

import com.app.car.Car;
import com.app.collectors.generic.CollectorGeneric;
import com.app.statistic.impl.CarStatistic;
import com.app.statistic.Statistic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;

/**
 * A {@link Collector} implementation that collects {@link Car} objects and computes statistical values (minimum, maximum, and average)
 * of a specified property of the cars.
 *
 * @param <T> The type of the property being collected (in this case, {@link BigDecimal}).
 */
public class CarStatisticCollector extends CollectorGeneric<Car, BigDecimal, Statistic<BigDecimal>> {

    /**
     * Constructs a new {@link CarStatisticCollector}.
     *
     * @param mapper A function that extracts a {@link BigDecimal} property from a {@link Car} object.
     */
    public CarStatisticCollector(Function<Car, BigDecimal> mapper) {
        super(mapper);
    }

    /**
     * Returns a {@link Function} that processes the list of {@link BigDecimal} values collected from the {@link Car} objects,
     * and computes the minimum, maximum, and average values, encapsulated in a {@link Statistic}.
     *
     * @return A {@link Function} that transforms a list of {@link BigDecimal} into a {@link Statistic} containing the minimum,
     *         maximum, and average values.
     */
    @Override
    public Function<List<BigDecimal>, Statistic<BigDecimal>> finisher() {
        return list -> {
            // Compute minimum value
            var min = list
                    .stream()
                    .min(Comparator.naturalOrder())
                    .orElse(BigDecimal.ZERO);

            // Compute maximum value
            var max = list
                    .stream()
                    .max(Comparator.naturalOrder())
                    .orElse(BigDecimal.ZERO);

            // Compute average value
            var avg = list
                    .stream()
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO)
                    .divide(BigDecimal.valueOf(list.size()), 2, RoundingMode.HALF_UP);

            // Return a new CarStatistic with the computed values
            return new CarStatistic<>(min, max, avg);
        };
    }
}
