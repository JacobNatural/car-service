package com.app.json.converter.impl;

import com.app.car.Car;
import com.app.color.Color;
import com.app.extension.json.converter.FileReaderExtension;
import com.app.extension.json.converter.GsonConverterExtension;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.FileReader;
import java.math.BigDecimal;
import java.util.List;

@ExtendWith({GsonConverterExtension.class, FileReaderExtension.class})
@AllArgsConstructor
public class GsonConverterFromJsonTest {
    private final GsonConverter<Car> gsonConverter;
    private final FileReader fileReader;

    @Test
    @DisplayName("When FileReader is null")
    @SneakyThrows
    public void test1() {

      Assertions
                    .assertThatThrownBy(
                            () -> gsonConverter.fromJson(null, Car.class)
                    )
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("FileReader is null");
    }

    @Test
    @DisplayName("When tClass is null")
    public void test2() {

        Assertions
                .assertThatThrownBy(
                        () -> gsonConverter.fromJson(fileReader, null)
                )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("tClass is null");
    }

    @Test
    @DisplayName("When FileReader read correct")
    public void test3(){

        Assertions
                .assertThat(gsonConverter.fromJson(fileReader,Car.class))
                .isEqualTo(new Car(
                        "BMW", "X3",
                        BigDecimal.valueOf(250000), 250,
                        Color.BLACK, List.of(
                                "ABS","AIR CONDITION"
                )));
    }
}

