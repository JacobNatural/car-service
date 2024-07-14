package com.app.json.serialize.impl;

import com.app.extension.json.serializer.CarsSerializerExtension;
import com.app.extension.service.CarServiceImplExtension;
import com.app.json.serialiaze.impl.CarsSerializer;
import com.app.service.impl.CarServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.app.data_provider.DataProvider.FILE_NAMES_DATA;

@ExtendWith({CarsSerializerExtension.class, CarServiceImplExtension.class})
@RequiredArgsConstructor
public class CarsSerializerTest {
    private final CarsSerializer carsSerializer;
    private final CarServiceImpl carService;

    @Test
    @DisplayName("When data is null")
    public void test1(){
        Assertions.assertThatThrownBy(
                        () -> carsSerializer.serializer(null, FILE_NAMES_DATA.get("test_save")
                        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Data is null");
    }

    @Test
    @DisplayName("When the filename is null")
    public void test2(){
        Assertions.assertThatThrownBy(
                        () -> carsSerializer.serializer(
                                carService, null)
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Filename is null");
    }

    @Test
    @DisplayName("When the filename is empty")
    public void test3(){
        Assertions.assertThatThrownBy(
                        () -> carsSerializer.serializer(
                                carService, "")
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Filename is empty");
    }

    @Test
    @DisplayName("When serialized data creates a file")
    public void test4(){

        carsSerializer.serializer(
                carService, FILE_NAMES_DATA.get("test_save"));

        Assertions
                .assertThat(
                        Files.exists(Paths.get(FILE_NAMES_DATA.get("test_save"))))
                .isTrue();
    }

    @AfterAll
    @DisplayName("Delete data")
    @SneakyThrows
    public static void deleteData(){
        Files.deleteIfExists(Paths.get(FILE_NAMES_DATA.get("test_save")));
    }
}
