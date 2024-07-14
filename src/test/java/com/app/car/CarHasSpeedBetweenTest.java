package com.app.car;

import com.app.extension.car.CarExtension;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@ExtendWith(CarExtension.class)
@RequiredArgsConstructor
public class CarHasSpeedBetweenTest {
    private final Car car;

    @ParameterizedTest
    @DisplayName("When car has speed between min and max")
    @CsvSource({"100,300","120,250","250,350" })
    public void test2(int minSpeed, int maxSpeed) {

        Assertions.assertThat(
                car.hasSpeedBetween(minSpeed, maxSpeed))
                .isTrue();
    }

    @ParameterizedTest
    @DisplayName("When car has not speed between min and max")
    @CsvSource({"100,150", "330,450"})
    public void test3(int minSpeed, int maxSpeed) {

        Assertions.assertThat(
                        car.hasSpeedBetween(minSpeed, maxSpeed))
                .isFalse();
    }
}
