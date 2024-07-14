package com.app.validate.impl;


import com.app.car.Car;
import com.app.color.Color;
import com.app.data_provider.DataProvider;
import com.app.extension.model.CarsExtension;
import com.app.extension.validate.CarsValidatorExtension;
import com.app.model.Cars;
import com.app.validate.Validator;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith({CarsExtension.class, CarsValidatorExtension.class})
@RequiredArgsConstructor
public class CarsValidatorValidateTest {

    private final CarsValidator carsValidator;

    @Test
    @DisplayName("When cars are null")
    public void test1() {

        Assertions.assertThatThrownBy(() ->
                Validator.validate(null, carsValidator))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cars is null");
    }

    @Test
    @DisplayName("When list of cars is null")
    public void test2() {

        Assertions.assertThatThrownBy(() ->
                        Validator.validate(new Cars(null), carsValidator))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("List of cars is null");
    }



    @Test
    @DisplayName("When list of cars is empty")
    public void test3() {

        Assertions.assertThatThrownBy(() ->
                        Validator.validate(new Cars(List.of()), carsValidator))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("List of cars is empty");
    }

    @Test
    @DisplayName("When the car has incorrect formats")
    public void test4() {

        var cars = List.of(new Car(
                "BmW", "X3",
                BigDecimal.valueOf(-1), 250,
                Color.BLACK, List.of("ABS", "AIR CONDITION")),
        new Car(
                "AUDI", "a1",
                BigDecimal.valueOf(300000), -30,
                Color.BLUE, List.of("RADIo", "ABS")
        ));

        Assertions.assertThatThrownBy(() ->
                Validator.validate(new Cars(cars), carsValidator))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("""                                 
                                Brand BmW does not match regex
                                Price -1 can't be less than 0
                                Model a1 does not match regex
                                Speed -30 can't be less than 0"""
                        );
    }

    @Test
    @DisplayName("When do not throw any exception")
    public void test5(){
        Assertions
                .assertThatNoException().isThrownBy(() ->
                        Validator.validate(
                                new Cars(DataProvider.CARS_DATA.get("cars1")), carsValidator));
    }

}
