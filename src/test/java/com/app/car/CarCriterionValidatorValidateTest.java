package com.app.car;

import com.app.extension.car.CarCriterionValidatorExtension;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(CarCriterionValidatorExtension.class)
@RequiredArgsConstructor
public class CarCriterionValidatorValidateTest {
    private final CarCriterionValidator criterionValidator;

    @Test
    @DisplayName("When the brand is null")
    public void test1(){

        var carCriterion = CarCriterion
                .builder()
                .brand(null)
                .model("X3")
                .minSpeed(140)
                .maxSpeed(240)
                .minPrice(BigDecimal.valueOf(100000))
                .maxPrice(BigDecimal.valueOf(200000))
                .components(List.of("ABS"))
                .build();

        Assertions.assertThat(criterionValidator.validate(carCriterion))
                .isEqualTo(List.of("Brand is null"));
    }

    @Test
    @DisplayName("When the brand is empty")
    public void test2(){

        var carCriterion = CarCriterion
                .builder()
                .brand("")
                .model("X3")
                .minSpeed(140)
                .maxSpeed(240)
                .minPrice(BigDecimal.valueOf(100000))
                .maxPrice(BigDecimal.valueOf(200000))
                .components(List.of("ABS"))
                .build();

        Assertions.assertThat(criterionValidator.validate(carCriterion))
                .isEqualTo(List.of("Brand is empty"));
    }

    @Test
    @DisplayName("When the model is null")
    public void test3(){

        var carCriterion = CarCriterion
                .builder()
                .brand("BMW")
                .model(null)
                .minSpeed(140)
                .maxSpeed(240)
                .minPrice(BigDecimal.valueOf(100000))
                .maxPrice(BigDecimal.valueOf(200000))
                .components(List.of("ABS"))
                .build();

        Assertions.assertThat(criterionValidator.validate(carCriterion))
                .isEqualTo(List.of("Model is null"));
    }

    @Test
    @DisplayName("When the model is empty")
    public void test4(){

        var carCriterion = CarCriterion
                .builder()
                .brand("BMW")
                .model("")
                .minSpeed(140)
                .maxSpeed(240)
                .minPrice(BigDecimal.valueOf(100000))
                .maxPrice(BigDecimal.valueOf(200000))
                .components(List.of("ABS"))
                .build();

        Assertions.assertThat(criterionValidator.validate(carCriterion))
                .isEqualTo(List.of("Model is empty"));
    }

    @Test
    @DisplayName("When the model is empty")
    public void test5(){

        var carCriterion = CarCriterion
                .builder()
                .brand("BMW")
                .model("")
                .minSpeed(140)
                .maxSpeed(240)
                .minPrice(BigDecimal.valueOf(100000))
                .maxPrice(BigDecimal.valueOf(200000))
                .components(List.of("ABS"))
                .build();

        Assertions.assertThat(criterionValidator.validate(carCriterion))
                .isEqualTo(List.of("Model is empty"));
    }

    @Test
    @DisplayName("When the minimum speed is greater than the maximum speed")
    public void test6(){

        var carCriterion = CarCriterion
                .builder()
                .brand("BMW")
                .model("X3")
                .minSpeed(140)
                .maxSpeed(100)
                .minPrice(BigDecimal.valueOf(100000))
                .maxPrice(BigDecimal.valueOf(200000))
                .components(List.of("ABS"))
                .build();

        Assertions.assertThat(criterionValidator.validate(carCriterion))
                .isEqualTo(List.of("Minimum speed is greater than maximum speed"));
    }

    @Test
    @DisplayName("When the speed is invalid")
    public void test7(){

        var carCriterion = CarCriterion
                .builder()
                .brand("BMW")
                .model("X3")
                .minSpeed(-50)
                .maxSpeed(20)
                .minPrice(BigDecimal.valueOf(100000))
                .maxPrice(BigDecimal.valueOf(220000))
                .components(List.of("ABS"))
                .build();

        Assertions.assertThat(criterionValidator.validate(carCriterion))
                .isEqualTo(List.of("Min speed less than 0"));
    }

    @Test
    @DisplayName("When the minimum price is null")
    public void test8(){

        var carCriterion = CarCriterion
                .builder()
                .brand("BMW")
                .model("X3")
                .minSpeed(140)
                .maxSpeed(200)
                .minPrice(null)
                .maxPrice(BigDecimal.valueOf(10000))
                .components(List.of("ABS"))
                .build();

        Assertions.assertThat(criterionValidator.validate(carCriterion))
                .isEqualTo(List.of("The minimum price or the maximum price is null"));
    }

    @Test
    @DisplayName("When the maximum price is null")
    public void test9(){

        var carCriterion = CarCriterion
                .builder()
                .brand("BMW")
                .model("X3")
                .minSpeed(140)
                .maxSpeed(200)
                .minPrice(BigDecimal.valueOf(10000))
                .maxPrice(null)
                .components(List.of("ABS"))
                .build();

        Assertions.assertThat(criterionValidator.validate(carCriterion))
                .isEqualTo(List.of("The minimum price or the maximum price is null"));
    }

    @Test
    @DisplayName("When the minimum price and maximum price are null")
    public void test10(){

        var carCriterion = CarCriterion
                .builder()
                .brand("BMW")
                .model("X3")
                .minSpeed(140)
                .maxSpeed(200)
                .minPrice(null)
                .maxPrice(null)
                .components(List.of("ABS"))
                .build();

        Assertions.assertThat(criterionValidator.validate(carCriterion))
                .isEqualTo(List.of("The minimum price or the maximum price is null"));
    }

    @Test
    @DisplayName("When the minimum price is greater than the maximum price")
    public void test11(){

        var carCriterion = CarCriterion
                .builder()
                .brand("BMW")
                .model("X3")
                .minSpeed(140)
                .maxSpeed(200)
                .minPrice(BigDecimal.valueOf(100000))
                .maxPrice(BigDecimal.valueOf(90000))
                .components(List.of("ABS"))
                .build();

        Assertions.assertThat(criterionValidator.validate(carCriterion))
                .isEqualTo(List.of("Minimum price is greater than maximum price"));
    }

    @Test
    @DisplayName("When the price is invalid")
    public void test12(){

        var carCriterion = CarCriterion
                .builder()
                .brand("BMW")
                .model("X3")
                .minSpeed(140)
                .maxSpeed(200)
                .minPrice(BigDecimal.valueOf(-4000))
                .maxPrice(BigDecimal.valueOf(90000))
                .components(List.of("ABS"))
                .build();

        Assertions.assertThat(criterionValidator.validate(carCriterion))
                .isEqualTo(List.of("Min price is less than 0"));
    }

    @Test
    @DisplayName("When the list of components is null")
    public void test13(){

        var carCriterion = CarCriterion
                .builder()
                .brand("BMW")
                .model("X3")
                .minSpeed(140)
                .maxSpeed(200)
                .minPrice(BigDecimal.valueOf(10000))
                .maxPrice(BigDecimal.valueOf(20000))
                .components(null)
                .build();

        Assertions.assertThat(criterionValidator.validate(carCriterion))
                .isEqualTo(List.of("Components is null"));
    }

    @Test
    @DisplayName("When the list of components is empty")
    public void test14(){

        var carCriterion = CarCriterion
                .builder()
                .brand("BMW")
                .model("X3")
                .minSpeed(140)
                .maxSpeed(200)
                .minPrice(BigDecimal.valueOf(10000))
                .maxPrice(BigDecimal.valueOf(20000))
                .components(List.of())
                .build();

        Assertions.assertThat(criterionValidator.validate(carCriterion))
                .isEqualTo(List.of("Components is empty"));
    }

    @Test
    @DisplayName("When a few parameters of the car criteria are invalid")
    public void test15(){

        var carCriterion = CarCriterion
                .builder()
                .brand(null)
                .model("")
                .minSpeed(140)
                .maxSpeed(100)
                .minPrice(BigDecimal.valueOf(10000))
                .maxPrice(BigDecimal.valueOf(20000))
                .components(List.of())
                .build();

        Assertions.assertThat(criterionValidator.validate(carCriterion))
                .containsExactlyInAnyOrder(
                        "Brand is null",
                        "Model is empty",
                        "Minimum speed is greater than maximum speed",
                        "Components is empty");
    }
}
