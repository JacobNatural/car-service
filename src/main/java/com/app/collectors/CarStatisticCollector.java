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

/**
 * The type Car statistic collector.
 */
    public class CarStatisticCollector extends CollectorGeneric<Car,BigDecimal,Statistic<BigDecimal>> {
    /**
     * Instantiates a new Car statistic collector.
     *
     * @param mapper the mapper
     */
    public CarStatisticCollector(Function<Car, BigDecimal> mapper) {
        super(mapper);
    }

    @Override
    public Function<List<BigDecimal>, Statistic<BigDecimal>> finisher() {
        return list ->{
          var min = list
                  .stream()
                  .min(Comparator.naturalOrder())
                  .orElse(BigDecimal.ZERO);

          var max = list
                  .stream()
                  .max(Comparator.naturalOrder())
                  .orElse(BigDecimal.ZERO);

          var avg = list
                  .stream()
                  .reduce(BigDecimal::add)
                  .orElse(BigDecimal.ZERO)
                  .divide(BigDecimal.valueOf(list.size()), 2, RoundingMode.HALF_UP);

          return new CarStatistic<>(min, max, avg);
        };
    }
}
