package com.app.car;

import com.app.extension.car.CarExtension;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

@ExtendWith(CarExtension.class)
@RequiredArgsConstructor
public class CarHasComponentsTest {
    private final Car car;

    @Test
    @DisplayName("When the car contains components")
    public void test1(){

        Assertions.assertThat(
                car.hasComponents(List.of("ABS", "AIR CONDITION")))
                .isTrue();
    }

    @Test
    @DisplayName("When only one component from the list is in the car")
    public void test2(){

        Assertions.assertThat(
                        car.hasComponents(List.of("ABS", "RADIO")))
                .isFalse();
    }

    @Test
    @DisplayName("When car does not contain any element")
    public void test3(){

        Assertions.assertThat(
                        car.hasComponents(List.of("HEATED SEATS", "RADIO")))
                .isFalse();
    }
}
