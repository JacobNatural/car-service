package com.app.json.converter.impl;

import com.app.car.Car;
import com.app.extension.car.CarExtension;
import com.app.extension.json.converter.FileWriterExtension;
import com.app.extension.json.converter.GsonConverterExtension;
import com.app.data_provider.DataProvider;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

@ExtendWith({GsonConverterExtension.class, CarExtension.class, FileWriterExtension.class})
@AllArgsConstructor
public class GsonConverterToJsonTest {

    private final GsonConverter<Car> gsonConverter;
    private final Car car;
    private final FileWriter fileWriter;

    @Test
    @DisplayName("When data is null")
    public void test1(){

        Assertions.assertThatThrownBy(
                () -> gsonConverter.toJson(null, fileWriter)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Data is null");
    }

    @Test
    @DisplayName("When the File Writer is null")
    public void test2(){

        Assertions.assertThatThrownBy(
                        () -> gsonConverter.toJson(car, null)
                )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("FileWriter is null");
    }

    @Test
    @DisplayName("When the file json exists")
    public void test3(){

        gsonConverter.toJson(car, fileWriter);

        Assertions.assertThat(
                Files.exists(
                        Paths.get(DataProvider.FILE_NAMES_DATA.get("test_save"))))
                .isTrue();
    }

    @AfterEach
    @DisplayName("Close data")
    @SneakyThrows
    public void closeData(){
        fileWriter.close();
    }

    @SneakyThrows
    @AfterAll
    @DisplayName("Clean data")
    public static void  cleanData(){
        Files.deleteIfExists(Paths.get(DataProvider.FILE_NAMES_DATA.get("test_save")));
    }
}
