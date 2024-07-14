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
public class CarGetDifferentBetweenPriceTest {

    private final Car car;

    @ParameterizedTest
    @DisplayName("When get correct different between prices")
    @CsvSource({"250000, 0","300000, 50000","200000,50000"})
    public void test2(BigDecimal price, BigDecimal expected){
        Assertions
                .assertThat(car.getDifferentBetweenPrice(price))
                .isEqualTo(expected);
    }
}

