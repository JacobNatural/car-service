package com.app.car;

import com.app.extension.car.CarExtension;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(CarExtension.class)
@RequiredArgsConstructor
public class CarHasCriterionTest {
    private final Car car;

    @Test
    @DisplayName("When the car has only two criteria")
    public void test1(){

        var criterion = CarCriterion.of(
                "BMW","X3",
                10,100,
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(150),
                List.of("ABS","HEATED SEATS"), new CarCriterionValidator()
                );

        Assertions.assertThat(car.hasCarCriterion(criterion))
                .isFalse();
    }

    @ParameterizedTest
    @DisplayName("When not found any criteria")
    @MethodSource("com.app.data_provider.DataProvider#carCriterionProvider")
    public void test2(CarCriterion criterion){
        Assertions.assertThat(car.hasCarCriterion(criterion))
                .isFalse();
    }

    @Test
    @DisplayName("When the car has only 3 criteria")
    public void test3(){

        var criterion = CarCriterion.of(
                "BMW","A3",
                10,300,
                BigDecimal.valueOf(100000),
                BigDecimal.valueOf(350000),
                List.of("ABS"),new CarCriterionValidator()
        );

        Assertions.assertThat(car.hasCarCriterion(criterion))
                .isFalse();
    }

    @Test
    @DisplayName("When the car has no criteria")
    public void test4(){
        var criterion = CarCriterion.of(
                "AUDI","A1",
                10,100,
                BigDecimal.valueOf(300000),
                BigDecimal.valueOf(350000),
                List.of("HEATED SEATS"), new CarCriterionValidator()
        );

        Assertions.assertThat(car.hasCarCriterion(criterion))
                .isFalse();
    }

    @Test
    @DisplayName("When the car has all criteria")
    public void test5(){

        var criterion = CarCriterion.of(
                "BMW","X3",
                10,300,
                BigDecimal.valueOf(100000),
                BigDecimal.valueOf(350000),
                List.of("ABS"),new CarCriterionValidator()
        );

        Assertions.assertThat(car.hasCarCriterion(criterion))
                .isTrue();
    }
}
