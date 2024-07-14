package com.app.car;

import com.app.color.Color;
import com.app.extension.car.CarExtension;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@ExtendWith(CarExtension.class)
@RequiredArgsConstructor
public class CarSortComponentsTest {
    private final Car car;

    @Test
    @DisplayName("When sort component ascending")
    public void test1(){
        Assertions
                .assertThat(car.carWithSortedComponents(Comparator.naturalOrder()))
                .isEqualTo(new Car(
                        "BMW", "X3",
                        BigDecimal.valueOf(250000),
                        250, Color.BLACK,
                        List.of("ABS", "AIR CONDITION", "CB RADIO")));
    }

    @Test
    @DisplayName("When sort component descending")
    public void test2(){
        Assertions
                .assertThat(car.carWithSortedComponents(Comparator.reverseOrder()))
                .isEqualTo(new Car(
                        "BMW", "X3",
                        BigDecimal.valueOf(250000),
                        250, Color.BLACK,
                        List.of( "CB RADIO", "AIR CONDITION","ABS")));
    }


}
