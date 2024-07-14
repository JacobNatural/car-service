package com.app.service;

import com.app.car.Car;
import com.app.data_provider.DataProvider;
import com.app.car.CarCriterion;
import com.app.repository.Repository;
import com.app.service.impl.CarServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class CarsServiceImplGetCarsWithCriterionTest {

    @Mock
    Repository<Car> repository;

    @InjectMocks
    CarServiceImpl carService;

    @Test
    @DisplayName("When car criteria is null")
    public void test1(){

        Assertions.assertThatThrownBy(
                () -> carService.getCarsWithCriterion(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Car criterion is null");
    }

    @Test
    @DisplayName("When the car was not found in the criteria")
    public void test2(){
        Mockito.when(repository.getAll())
                .thenReturn(DataProvider.CARS_DATA.get("cars1"));

        Assertions.assertThat(
                carService.getCarsWithCriterion(
                        DataProvider.CAR_CRITERIA.get("criterion2").get(3)))
                .isEqualTo(List.of());

        Mockito.verify(repository, Mockito.times(1)).getAll();
    }

    @ParameterizedTest
    @DisplayName("When searching for cars by criteria")
    @MethodSource("com.app.data_provider.DataProvider#getCarsCriterionProvider")
    public void test3(List<Car> cars1, CarCriterion carCriterion, List<Car> cars2) {
        Mockito.when(repository.getAll())
                .thenReturn(cars1);

        Assertions.assertThat(
                carService.getCarsWithCriterion(carCriterion))
                .isEqualTo(cars2);

        Mockito.verify(repository, Mockito.times(1)).getAll();

    }
}
