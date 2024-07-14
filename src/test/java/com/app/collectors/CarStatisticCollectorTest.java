package com.app.collectors;

import com.app.car.CarMapper;
import com.app.data_provider.DataProvider;
import com.app.repository.impl.CarsRepositoryImpl;
import com.app.statistic.impl.CarStatistic;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class CarStatisticCollectorTest {

    @Mock
    private CarsRepositoryImpl cars;

    @Test
    @DisplayName("When to calculate correct prices")
    public void test1() {
        Mockito.when(cars.getAll()).thenReturn(
                DataProvider.CARS_DATA.get("cars1"));

        var collector = new CarStatisticCollector(CarMapper.toPrice);

        Assertions.assertThat(cars.getAll().stream().collect(collector))
                .isEqualTo(
                        new CarStatistic<>(
                                BigDecimal.valueOf(120000),
                                BigDecimal.valueOf(300000),
                                BigDecimal.valueOf(223333.33)));
    }

    @Test
    @DisplayName("When to calculate correct speed")
    public void test2() {

        Mockito.when(cars.getAll()).thenReturn(
                DataProvider.CARS_DATA.get("cars1"));

        var collector = new CarStatisticCollector(CarMapper.toSpeed);

        Assertions.assertThat(cars.getAll().stream().collect(collector))
                .isEqualTo(
                        new CarStatistic<>(
                                BigDecimal.valueOf(170),
                                BigDecimal.valueOf(280),
                                BigDecimal.valueOf(233.33)));
    }
}
