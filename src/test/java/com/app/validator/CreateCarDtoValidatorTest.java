package com.app.validator;

import com.app.color.Color;
import com.app.controller.dto.car.CreateCarDto;
import com.app.validate.CreateCarDtoValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest(classes = CreateCarDtoValidator.class)
public class CreateCarDtoValidatorTest {

    @Autowired
    private CreateCarDtoValidator createCarDtoValidator;

    @Value("${validate.car.regex.model}")
    private String modelRegex;

    @Value("${validate.car.regex.brand}")
    private String brandRegex;

    @Value("${validate.car.min.speed}")
    private int minimumSpeed;

    @Value("${validate.car.min.price}")
    private BigDecimal minimumPrice;


    @Test
    @DisplayName("When validating CreateCarDto, if the model is null, throw an IllegalArgumentException.")
    public void test1() {

        Assertions.assertThatThrownBy(() -> createCarDtoValidator.validate(
                        new CreateCarDto("BMW", null, 100, BigDecimal.valueOf(100000), Color.BLACK, List.of(1L, 2L)))
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Model cannot be null.");
    }

    @Test
    @DisplayName("When validation CreateCarDto, if the model is empty, throw an IllegalArgumentException.")
    public void test2() {

        Assertions.assertThatThrownBy(() -> createCarDtoValidator.validate(
                        new CreateCarDto("BMW", "", 100, BigDecimal.valueOf(100000), Color.BLACK, List.of(1L, 2L)))
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Model cannot be empty.");
    }

    @Test
    @DisplayName("When validation CreateCarDto, if the model is invalid, throw an IllegalArgumentException.")
    public void test3() {

        Assertions.assertThatThrownBy(() -> createCarDtoValidator.validate(
                        new CreateCarDto("BMW", "X2<>", 100, BigDecimal.valueOf(100000), Color.BLACK, List.of(1L, 2L)))
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The model does not match the pattern: " + modelRegex + ".");
    }

    @Test
    @DisplayName("When validation CreateCarDto, if the brand is null, throw an IllegalArgumentException.")
    public void test4() {

        Assertions.assertThatThrownBy(() -> createCarDtoValidator.validate(
                        new CreateCarDto(null, "X3", 100, BigDecimal.valueOf(100000), Color.BLACK, List.of(1L, 2L)))
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Brand cannot be null.");
    }

    @Test
    @DisplayName("When validation CreateCarDto, if the brand is empty, throw an IllegalArgumentException.")
    public void test5() {

        Assertions.assertThatThrownBy(() -> createCarDtoValidator.validate(
                        new CreateCarDto("", "X3", 100, BigDecimal.valueOf(100000), Color.BLACK, List.of(1L, 2L)))
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Brand cannot be empty.");
    }

    @Test
    @DisplayName("When validation CreateCarDto, if the brand is invalid, throw an IllegalArgumentException.")
    public void test6() {
        Assertions.assertThatThrownBy(() -> createCarDtoValidator.validate(
                        new CreateCarDto("BMW?", "X3", 100, BigDecimal.valueOf(100000), Color.BLACK, List.of(1L, 2L)))
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The brand does not match the pattern: " + brandRegex + ".");
    }

    @Test
    @DisplayName("When validation CreateCarDto, if the brand is too low, throw an IllegalArgumentException.")
    public void test7() {
        Assertions.assertThatThrownBy(() -> createCarDtoValidator.validate(
                        new CreateCarDto("BMW", "X3", -10, BigDecimal.valueOf(100000), Color.BLACK, List.of(1L, 2L)))
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The speed does not match the minimum speed: " + minimumSpeed + ".");
    }

    @Test
    @DisplayName("When validation CreateCarDto, if the price is null, throw an IllegalArgumentException.")
    public void test8() {
        Assertions.assertThatThrownBy(() -> createCarDtoValidator.validate(
                        new CreateCarDto("BMW", "X3", 210, null, Color.BLACK, List.of(1L, 2L)))
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Price cannot be null.");
    }

    @Test
    @DisplayName("When validation CreateCarDto, if the price is too low, throw an IllegalArgumentException.")
    public void test9() {
        Assertions.assertThatThrownBy(() -> createCarDtoValidator.validate(
                        new CreateCarDto("BMW", "X3", 210, BigDecimal.valueOf(5000), Color.BLACK, List.of(1L, 2L)))
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The price does not match the minimum price: " + minimumPrice + ".");
    }

    @Test
    @DisplayName("When validation CreateCarDto, if the components is null, throw an IllegalArgumentException.")
    public void test10() {
        Assertions.assertThatThrownBy(() -> createCarDtoValidator.validate(
                        new CreateCarDto("BMW", "X3", 210, BigDecimal.valueOf(500000), Color.BLACK, null))
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Components cannot be null.");
    }

    @Test
    @DisplayName("When validation CreateCarDto, if the components is empty, throw an IllegalArgumentException.")
    public void test11() {
        Assertions.assertThatThrownBy(() -> createCarDtoValidator.validate(
                        new CreateCarDto("BMW", "X3", 210, BigDecimal.valueOf(500000), Color.BLACK, List.of()))
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Components cannot be empty.");
    }

    @Test
    @DisplayName("When validation CreateCarDto, not throw an Exception.")
    public void test12() {
        Assertions.assertThatNoException().isThrownBy(() -> createCarDtoValidator.validate(
                new CreateCarDto("BMW", "X3", 210, BigDecimal.valueOf(500000), Color.BLACK, List.of(1L, 2L)))
        );
    }

}
