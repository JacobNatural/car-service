package com.app.service;

import com.app.car.Car;
import com.app.car.CarMapper;
import com.app.collectors.CarStatisticCollector;
import com.app.data_provider.DataProvider;
import com.app.repository.Repository;
import com.app.service.impl.CarServiceImpl;
import com.app.statistic.impl.CarStatistic;
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

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class CarsServiceImplGetStatisticTest {

    @Mock
    private Repository<Car> repository;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    @DisplayName("When the collector is null")
    public void test1(){

        Assertions.assertThatThrownBy(
                () -> carService.getStatistic(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Collector is null");
    }

    @Test
    @DisplayName("When get speed statistic")
    public void test2(){

        Mockito.when(repository.getAll())
                .thenReturn(DataProvider.CARS_DATA.get("cars1"));

        Assertions.assertThat(
                carService.getStatistic(new CarStatisticCollector(CarMapper.toSpeed))
        )
                .isEqualTo(new CarStatistic<>(
                BigDecimal.valueOf(170),
                BigDecimal.valueOf(280),
                BigDecimal.valueOf(233.33)
        ));
    }

    @Test
    @DisplayName("When get price statistic")
    public void test3(){

        Mockito.when(repository.getAll())
                .thenReturn(DataProvider.CARS_DATA.get("cars2"));

        Assertions.assertThat(
                        carService.getStatistic(new CarStatisticCollector(CarMapper.toPrice))
                )
                .isEqualTo(new CarStatistic<>(
                        BigDecimal.valueOf(120000),
                        BigDecimal.valueOf(300000),
                        BigDecimal.valueOf(223333.33)
                ));

        Mockito.verify(repository, Mockito.times(1)).getAll();
    }

}
