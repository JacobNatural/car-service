package com.app.service;

import com.app.car.Car;
import com.app.car.CarMapper;
import com.app.data_provider.DataProvider;
import com.app.repository.Repository;
import com.app.service.impl.CarServiceImpl;
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
public class CarsServiceImplGroupByAndAmountOfCarsTest {

    @Mock
    private Repository<Car> repository;

    @InjectMocks
    private CarServiceImpl carService;

    @BeforeEach
    public void setUp(TestInfo testInfo){

        if("test1".equals(testInfo.getTestMethod().orElseThrow().getName())){
            return;
        }

        Mockito
                .when(repository.getAll())
                .thenReturn(DataProvider.CARS_DATA.get("cars2"));
    }

    @Test
    @DisplayName("When the car mapper is null")
    public void test1(){
        Assertions
                .assertThatThrownBy(
                        () -> carService.groupByAndAmountOfCars(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Car mapper is null");
    }

    @Test
    @DisplayName("When group by brand and amount of cars")
    public void test2(){
        Assertions
                .assertThat(carService.groupByAndAmountOfCars(CarMapper.toBrand))
                .isEqualTo(Map.of("AUDI", 2L, "BMW", 1L));

        Mockito.verify(repository, Mockito.times(1)).getAll();
    }

    @Test
    @DisplayName("When group by speed and amount of cars")
    public void test3(){
        Assertions
                .assertThat(carService.groupByAndAmountOfCars(CarMapper.toSpeed))
                .isEqualTo(Map.of(
                        BigDecimal.valueOf(170),1L,
                        BigDecimal.valueOf(250), 1L,
                        BigDecimal.valueOf(280),1L));

        Mockito.verify(repository, Mockito.times(1)).getAll();
    }


}
