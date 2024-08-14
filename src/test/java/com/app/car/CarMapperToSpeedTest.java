package com.app.car;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.app.data_provider.DataProvider.CARS_DATA;

public class CarMapperToSpeedTest {

    @Test
    @DisplayName("When the car mapper obtains correct data")
    public void test1(){
        Assertions.assertThat(
                        CarMapper.toSpeed.apply(CARS_DATA.get("cars1").getFirst()))
                .isEqualTo(BigDecimal.valueOf(250));

    }
}
