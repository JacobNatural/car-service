package com.app.collectors;

import com.app.car.CarMapper;
import com.app.data_provider.DataProvider;
import com.app.repository.impl.CarsRepositoryImpl;
import com.app.statistic.impl.CarMinMax;
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
public class CarMinMaxCollectorTest {

    @Mock
    private CarsRepositoryImpl cars;

    @Test
    @DisplayName("When you get min, max for prices")
    public void test1(){

        Mockito.when(cars.getAll())
                .thenReturn(
                DataProvider.CARS_DATA.get("cars1"));

        var collector = new CarMinMaxCollector(CarMapper.toPrice);

        Assertions.assertThat(
                cars.getAll().stream().collect(collector))
                .isEqualTo(
                        new CarMinMax<>(
                                BigDecimal.valueOf(120000),
                                BigDecimal.valueOf(300000)));
    }

    @Test
    @DisplayName("When you get min, max for speed")
    public void test2(){

        Mockito.when(cars.getAll())
                .thenReturn(DataProvider.CARS_DATA.get("cars1"));

        var collector = new CarMinMaxCollector(CarMapper.toSpeed);

        Assertions.assertThat(
                        cars.getAll().stream().collect(collector))
                .isEqualTo(
                        new CarMinMax<>(
                                BigDecimal.valueOf(170),
                                BigDecimal.valueOf(280)));
    }
}
