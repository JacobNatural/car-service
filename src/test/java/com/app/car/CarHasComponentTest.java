package com.app.car;

import com.app.extension.car.CarExtension;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(CarExtension.class)
@RequiredArgsConstructor
public class CarHasComponentTest {

    private final Car car;

    @Test
    @DisplayName("When car has component")
    public void test1(){
        Assertions
                .assertThat(car.hasComponent("ABS"))
                .isTrue();

    }

    @Test
    @DisplayName("When car has not component")
    public void test2(){
        Assertions
                .assertThat(car.hasComponent("RADIO"))
                .isFalse();
    }

}
