package com.app.service;

import com.app.car.Car;
import com.app.car.CarMapper;
import com.app.collectors.CarMinMaxCollector;
import com.app.color.Color;
import com.app.data_provider.DataProvider;
import com.app.repository.Repository;
import com.app.service.impl.CarServiceImpl;
import com.app.statistic.impl.CarMinMax;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
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
public class CarsServiceImplGroupByAndMinMaxPriceStatisticTest {

    @Mock
    private Repository<Car> repository;

    @InjectMocks
    private CarServiceImpl carService;

    @BeforeEach
    void setUp(TestInfo testInfo) {

        var methodName = testInfo.getTestMethod().orElseThrow().getName();

        if("test1".equals(methodName) || "test2".equals(methodName)) {
            return;
        }

        Mockito
                .when(repository.getAll())
                .thenReturn(DataProvider.CARS_DATA.get("cars3"));
    }

    @Test
    @DisplayName("When the car mapper is null")
    public void test1(){

        Assertions
                .assertThatThrownBy(
                        () -> carService.groupByAndMinMaxPriceStatistic(
                                null, new CarMinMaxCollector(CarMapper.toPrice)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Car mapper is null");
    }

    @Test
    @DisplayName("When the car mapper is null")
    public void test2(){

        Assertions
                .assertThatThrownBy(
                        () -> carService.groupByAndMinMaxPriceStatistic(
                                CarMapper.toColor, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Collector is null");
    }

    @Test
    @DisplayName("When group by color and min max speed statistic")
    public void test3(){
        Assertions
                .assertThat(
                        carService.groupByAndMinMaxPriceStatistic(
                                CarMapper.toColor, new CarMinMaxCollector(
                                        CarMapper.toSpeed
                                )
                        ))
                .isEqualTo(Map.of(
                        Color.BLACK,
                        new CarMinMax<>(BigDecimal.valueOf(250), BigDecimal.valueOf(290)),
                                Color.BLUE,
                        new CarMinMax<>(BigDecimal.valueOf(170),BigDecimal.valueOf(280)),
                        Color.RED,
                        new CarMinMax<>(BigDecimal.valueOf(195),BigDecimal.valueOf(195))));

        Mockito.verify(repository, Mockito.times(1)).getAll();
    }

    @Test
    @DisplayName("When group by color and min max price statistic")
    public void test4(){
        Assertions
                .assertThat(
                        carService.groupByAndMinMaxPriceStatistic(
                                CarMapper.toColor, new CarMinMaxCollector(
                                        CarMapper.toPrice
                                )
                        ))
                .isEqualTo(Map.of(
                        Color.BLACK,
                        new CarMinMax<>(BigDecimal.valueOf(250000), BigDecimal.valueOf(350000)),
                        Color.BLUE,
                        new CarMinMax<>(BigDecimal.valueOf(120000),BigDecimal.valueOf(300000)),
                        Color.RED,
                        new CarMinMax<>(BigDecimal.valueOf(200000),BigDecimal.valueOf(200000))));

        Mockito.verify(repository, Mockito.times(1)).getAll();
    }

}
