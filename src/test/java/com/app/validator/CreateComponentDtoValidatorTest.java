package com.app.validator;

import com.app.controller.dto.components.CreateComponentDto;
import com.app.validate.CreateComponentDtoValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = CreateComponentDtoValidator.class)
public class CreateComponentDtoValidatorTest {

    @Value("${validate.components.regex.name}")
    private String nameRegex;

    @Autowired
    private CreateComponentDtoValidator createComponentDtoValidator;

    @Test
    @DisplayName("When validation CreateCarDto, if the name is null, throw an IllegalArgumentException.")
    public void test1() {

        Assertions.assertThatThrownBy(() -> createComponentDtoValidator.validate(new CreateComponentDto(null)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Component name cannot be null.");

    }

    @Test
    @DisplayName("When validation CreateCarDto, if the name is empty, throw an IllegalArgumentException.")
    public void test2() {

        Assertions.assertThatThrownBy(() -> createComponentDtoValidator.validate(new CreateComponentDto("")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Component name cannot be empty.");
    }

    @Test
    @DisplayName("When validation CreateCarDto, if the name is invalid, throw an IllegalArgumentException.")
    public void test3() {

        Assertions.assertThatThrownBy(() -> createComponentDtoValidator.validate(new CreateComponentDto("Small-Radio")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The name does not match the pattern: " + nameRegex + ".");
    }

    @Test
    @DisplayName("When validation CreateCarDto, do not throw an Exception.")
    public void test4() {

        Assertions.assertThatNoException().isThrownBy(() ->
                createComponentDtoValidator.validate(new CreateComponentDto("RADIO")));
    }
}
