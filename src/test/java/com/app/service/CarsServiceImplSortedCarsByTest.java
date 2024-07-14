package com.app.service;

import com.app.car.Car;
import com.app.car.CarMapper;
import com.app.data_provider.DataProvider;
import com.app.repository.Repository;
import com.app.service.impl.CarServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import java.util.Comparator;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class CarsServiceImplSortedCarsByTest {

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
                .thenReturn(DataProvider.CARS_DATA.get("cars1"));
    }

    @Test
    @DisplayName("When to comparator is null")
    public void test1(){
        Assertions
                .assertThatThrownBy(() -> carService.sortedCarsBy(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Comparator is null");
    }

    @Test
    @DisplayName("When to compare by Brand")
    public void test2(){

        Assertions
                .assertThat(carService.sortedCarsBy(
                        Comparator.comparing(CarMapper.toBrand)))
                .isEqualTo(List.of(
                        DataProvider.CARS_DATA.get("cars1").get(1),
                        DataProvider.CARS_DATA.get("cars1").getFirst(),
                                DataProvider.CARS_DATA.get("cars1").get(2))
                        );

        Mockito.verify(repository,Mockito.times(1)).getAll();
    }

    @Test
    @DisplayName("When the compare by Brand and by speed")
    public void test3(){

        Assertions
                .assertThat(carService.sortedCarsBy(
                        Comparator.comparing(CarMapper.toBrand)
                                .thenComparing(CarMapper.toSpeed)))

                .isEqualTo(List.of(
                        DataProvider.CARS_DATA.get("cars1").get(1),
                        DataProvider.CARS_DATA.get("cars1").getFirst(),
                        DataProvider.CARS_DATA.get("cars1").getLast())
                );

        Mockito.verify(repository,Mockito.times(1)).getAll();
    }

    @Test
    @DisplayName("When compare by price descending")
    public void test4(){

        Assertions
                .assertThat(carService.sortedCarsBy(
                        Comparator.comparing(CarMapper.toPrice).reversed()))

                .isEqualTo(List.of(
                        DataProvider.CARS_DATA.get("cars1").get(1),
                        DataProvider.CARS_DATA.get("cars1").getFirst(),
                        DataProvider.CARS_DATA.get("cars1").getLast())
                );

        Mockito.verify(repository,Mockito.times(1)).getAll();
    }
}
