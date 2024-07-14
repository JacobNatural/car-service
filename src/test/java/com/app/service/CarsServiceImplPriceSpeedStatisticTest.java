package com.app.service;

import com.app.car.Car;
import com.app.repository.Repository;
import com.app.service.impl.CarServiceImpl;
import com.app.statistic.impl.CarStatistic;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class CarsServiceImplPriceSpeedStatisticTest {

    @Mock
    private Repository<Car> repository;

    @InjectMocks
    private CarServiceImpl carService;


    @ParameterizedTest
    @DisplayName("When get price and speed statistic")
    @MethodSource("com.app.data_provider.DataProvider#speedPriceStatisticProvider")
    public void test2(List<Car> cars, List<CarStatistic<BigDecimal>> carStatistics){

        Mockito
                .when(repository.getAll())
                .thenReturn(cars);

        Assertions
                .assertThat(carService.priceSpeedStatistic())
                .isEqualTo(carStatistics);

        Mockito.verify(repository, Mockito.times(2)).getAll();
    }
}
