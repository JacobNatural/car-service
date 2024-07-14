package com.app.car;

import com.app.extension.car.CarExtension;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(CarExtension.class)
@RequiredArgsConstructor
public class CarHasModelTest {
    private final Car car;


    @Test
    @DisplayName("When car has a model pattern")
    public void test1(){
        Assertions
                .assertThat(car.hasModelPattern("X3"))
                .isTrue();
    }

    @Test
    @DisplayName("When car has not a model pattern")
    public void test2(){
        Assertions
                .assertThat(car.hasModelPattern("A1"))
                .isFalse();
    }
}
