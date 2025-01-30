package com.app.validator;

import com.app.validate.ParameterCarValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ParameterCarValidatorTest {

    @InjectMocks
    private ParameterCarValidator parameterCarValidator;

    @Test
    @DisplayName("When validating the parameter, if the parameter is null, throw an IllegalArgumentException.")
    public void test1() {

        Assertions.assertThatThrownBy(() -> parameterCarValidator.validate(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Parameter cannot be null");
    }

    @Test
    @DisplayName("When validating the parameter, if the parameter is empty, throw an IllegalArgumentException.")
    public void test2() {

        Assertions.assertThatThrownBy(() -> parameterCarValidator.validate(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Parameter cannot be empty");
    }

    @Test
    @DisplayName("When validating the parameter, if the parameter contains an invalid value, throw an IllegalArgumentException.")
    public void test3() {

        Assertions.assertThatThrownBy(() -> parameterCarValidator.validate("sun"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid parameter value: sun");
    }

    @ParameterizedTest
    @DisplayName("When validating the parameter, parameter is correct, do not throw an Exception.")
    @CsvSource({"id", "brand", "model", "price", "speed", "color", "components"})
    public void test4(String parameter) {

        Assertions.assertThatNoException()
                .isThrownBy(() -> parameterCarValidator.validate(parameter));
    }
}
