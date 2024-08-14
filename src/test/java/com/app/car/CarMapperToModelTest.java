package com.app.car;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.app.data_provider.DataProvider.CARS_DATA;

public class CarMapperToModelTest {

    @Test
    @DisplayName("When the car mapper obtains correct data")
    public void test1(){
        Assertions.assertThat(
                        CarMapper.toModel.apply(CARS_DATA.get("cars1").getFirst()))
                .isEqualTo("X3");

    }
}