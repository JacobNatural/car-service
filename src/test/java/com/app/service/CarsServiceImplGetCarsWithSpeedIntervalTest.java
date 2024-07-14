package com.app.service;

import com.app.car.Car;
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

import java.util.List;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class CarsServiceImplGetCarsWithSpeedIntervalTest {

    @Mock
    private Repository<Car> repository;

    @InjectMocks
    private CarServiceImpl carService;

    @BeforeEach
    void setUp(TestInfo testInfo) {

        if("test1".equals(testInfo.getTestMethod().orElseThrow().getName())){
            return;
        }
        Mockito.when(repository.getAll())
                .thenReturn(DataProvider.CARS_DATA.get("cars1"));
    }

    @Test
    @DisplayName("When min is greater than max")
    public void test1() {

        Assertions.assertThatThrownBy(
                        () -> carService.getCarsWithSpeedInterval(400, 300))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Min speed is greater than max speed");

    }

@Test
@DisplayName("When two cars were found")
public void test2() {

    Assertions.assertThat(carService.getCarsWithSpeedInterval(200, 300))
            .isEqualTo(List.of(
                    DataProvider.CARS_DATA.get("cars1").getFirst(),
                    DataProvider.CARS_DATA.get("cars1").get(1)));
    Mockito.verify(repository, Mockito.times(1)).getAll();
    }

    @Test
    @DisplayName("When no cars were found")
    public void test3() {

        Assertions.assertThat(carService.getCarsWithSpeedInterval(100, 120))
                .isEqualTo(List.of());

        Mockito.verify(repository, Mockito.times(1)).getAll();
    }
}
