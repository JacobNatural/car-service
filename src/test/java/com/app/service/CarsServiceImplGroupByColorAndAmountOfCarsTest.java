package com.app.service;

import com.app.car.Car;
import com.app.color.Color;
import com.app.data_provider.DataProvider;
import com.app.repository.Repository;
import com.app.service.impl.CarServiceImpl;
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

import java.util.Map;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class CarsServiceImplGroupByColorAndAmountOfCarsTest {

    @Mock
    private Repository<Car> repository;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    @DisplayName("When group by color and amount of cars - first data")
    public void test1(){
        Mockito.when(repository.getAll())
                .thenReturn(DataProvider.CARS_DATA.get("cars1"));

        Assertions
                .assertThat(carService.groupByColorAndAmountOfCars())
                .isEqualTo(Map.of(
                        Color.BLACK, 1L,
                        Color.BLUE, 1L,
                        Color.RED, 1L));

        Mockito.verify(repository, Mockito.times(1)).getAll();
    }

    @Test
    @DisplayName("When group by color and amount of cars - second data")
    public void test2(){
        Mockito.when(repository.getAll())
                .thenReturn(DataProvider.CARS_DATA.get("cars2"));

        Assertions
                .assertThat(carService.groupByColorAndAmountOfCars())
                .isEqualTo(Map.of(
                        Color.BLACK, 1L,
                        Color.BLUE, 2L));

        Mockito.verify(repository, Mockito.times(1)).getAll();
    }
}
