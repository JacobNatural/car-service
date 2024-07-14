package com.app.validate.impl;

import com.app.car.Car;
import com.app.car.CarValidator;
import com.app.color.Color;
import com.app.data_provider.DataProvider;
import com.app.extension.validate.CarValidatorExtension;
import com.app.validate.Validator;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(CarValidatorExtension.class)
@RequiredArgsConstructor
public class CarValidatorValidateTest {
    private final CarValidator carValidator;

    @Test
    @DisplayName("When the car is null")
    public void test1(){
        Assertions.assertThatThrownBy(
                () -> Validator.validate(null, carValidator))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Car is null");
    }


    @Test
    @DisplayName("When thr brand has incorrect format")
    public void test2(){
        var car = new Car(
                "Bmw", "X3",
                BigDecimal.valueOf(250000), 250,
                Color.BLACK, List.of("ABS", "AIR CONDITION"));

        Assertions.assertThatThrownBy(
                        () -> Validator.validate(car, carValidator))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Brand Bmw does not match regex");
    }

    @Test
    @DisplayName("When the model has incorrect format")
    public void test3(){
        var car = new Car(
                "BMW", "x3",
                BigDecimal.valueOf(250000), 250,
                Color.BLACK, List.of("ABS", "AIR CONDITION"));

        Assertions.assertThatThrownBy(
                        () -> Validator.validate(car, carValidator))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Model x3 does not match regex");
    }

    @Test
    @DisplayName("When the price is too low")
    public void test4(){
        var car = new Car(
                "BMW", "X3",
                BigDecimal.valueOf(-10), 250,
                Color.BLACK, List.of("ABS", "AIR CONDITION"));

        Assertions.assertThatThrownBy(
                        () -> Validator.validate(car, carValidator))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Price -10 can't be less than 0");
    }

    @Test
    @DisplayName("When the speed is too low")
    public void test5(){
        var car = new Car(
                "BMW", "X3",
                BigDecimal.valueOf(250000), -30,
                Color.BLACK, List.of("ABS", "AIR CONDITION"));

        Assertions.assertThatThrownBy(
                        () -> Validator.validate(car, carValidator))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Speed -30 can't be less than 0");
    }


    @Test
    @DisplayName("When the component has incorrect format")
    public void test6(){
        var car = new Car(
                "BMW", "X3",
                BigDecimal.valueOf(250000), 250,
                Color.BLACK, List.of("AbS", "AIR CONDITION"));

        Assertions.assertThatThrownBy(
                        () -> Validator.validate(car, carValidator))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Component AbS does not match regex");
    }

    @Test
    @DisplayName("When do not throw any exception")
    public void test7(){
        Assertions
                .assertThatNoException().isThrownBy(() ->
                        Validator.validate(
                                DataProvider.CAR_DATA.get("car1"), carValidator));
    }
}
