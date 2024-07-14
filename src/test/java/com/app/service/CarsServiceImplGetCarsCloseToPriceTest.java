package com.app.service;

import com.app.car.Car;
import com.app.data_provider.DataProvider;
import com.app.repository.Repository;
import com.app.service.impl.CarServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
public class CarsServiceImplGetCarsCloseToPriceTest {

    @Mock
    Repository<Car> repository;

    @InjectMocks
    CarServiceImpl carService;

    @Test
    @DisplayName("When the price is null")
    public void test1(){

        Assertions.assertThatThrownBy(
                () -> carService.getCarsCloseToPrice(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Price is null");
    }

    @ParameterizedTest
    @DisplayName("When the price is too low")
    @CsvSource({"-1", "0"})
    public void test2(BigDecimal price){

        Assertions.assertThatThrownBy(
                        () -> carService.getCarsCloseToPrice(price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Price is lower than zero");
    }


    @ParameterizedTest
    @DisplayName("When cars are close to the prices")
    @MethodSource("com.app.data_provider.DataProvider#priceCloseToPriceProvider")
    public void test3(BigDecimal price, List<Car> cars){

        Mockito.when(repository.getAll())
                .thenReturn(DataProvider.CARS_DATA.get("cars3"));

        Assertions.assertThat(
                carService.getCarsCloseToPrice(price))
                .isEqualTo(cars);

        Mockito.verify(repository, Mockito.times(1)).getAll();
    }

}
