package com.app.car;

import com.app.extension.car.CarExtension;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

@ExtendWith(CarExtension.class)
@RequiredArgsConstructor
public class CarHasPriceBetweenTest {
    private final Car car;


    @ParameterizedTest
    @DisplayName("When car has price between")
    @CsvSource({"150000,280000","250000,300000","100000,250000"})
    public void test1(BigDecimal minPrice, BigDecimal maxPrice){
        Assertions.assertThat(
                car.hasPriceBetween(minPrice,maxPrice))
                .isTrue();
    }

    @ParameterizedTest
    @DisplayName("When car has not price between")
    @CsvSource({"150000,249999","250001,350000","300000,350000"})
    public void test2(BigDecimal minPrice, BigDecimal maxPrice){
        Assertions.assertThat(
                car.hasPriceBetween(minPrice,maxPrice))
                .isFalse();
    }
}
