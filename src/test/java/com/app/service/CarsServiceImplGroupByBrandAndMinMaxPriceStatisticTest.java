package com.app.service;

import com.app.car.Car;
import com.app.data_provider.DataProvider;
import com.app.repository.Repository;
import com.app.service.impl.CarServiceImpl;
import com.app.statistic.impl.CarMinMax;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class CarsServiceImplGroupByBrandAndMinMaxPriceStatisticTest {

    @Mock
    private Repository<Car> repository;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    @DisplayName("When group by brand and min max price statistic")
    public void test1(){
        Mockito
                .when(repository.getAll())
                .thenReturn(DataProvider.CARS_DATA.get("cars3"), DataProvider.CARS_DATA.get("cars1"));

        Assertions.assertThat(carService.groupByBrandAndMinMaxPriceStatistic())
                .isEqualTo(Map.of(
                        "AUDI",
                        new CarMinMax<>(
                                BigDecimal.valueOf(120000),
                                BigDecimal.valueOf(300000)),
                        "BMW",
                        new CarMinMax<>(
                                BigDecimal.valueOf(250000),
                                BigDecimal.valueOf(350000))));

        Mockito.verify(repository, Mockito.times(1)).getAll();
    }
}
