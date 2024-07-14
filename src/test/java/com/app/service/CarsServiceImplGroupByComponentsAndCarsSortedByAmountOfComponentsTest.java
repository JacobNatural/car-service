package com.app.service;

import com.app.car.Car;
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

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class CarsServiceImplGroupByComponentsAndCarsSortedByAmountOfComponentsTest {

    @Mock
    private Repository<Car> repository;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    @DisplayName("When the comparator is null")
    public void test1(){

        Assertions.assertThatThrownBy(
                () -> carService.groupByComponentsAndCarsSortedByAmountOfComponents(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Comparator is null");
    }

    @ParameterizedTest
    @DisplayName("When group and sort by amount of components")
    @MethodSource("com.app.data_provider.DataProvider#groupByComponentsAndCarSortedByAmountComponentsProvider")
    public void test2(List<Car> cars, Map<String, List<Car>> groupedCars ){

        Mockito.when(repository.getAll())
                .thenReturn(cars);

        Assertions.assertThat(
                carService.groupByComponentsAndCarsSortedByAmountOfComponents(Comparator.naturalOrder()))
                .isEqualTo(groupedCars);

        Mockito.verify(repository, Mockito.times(1)).getAll();

    }
}
