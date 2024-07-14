package com.app.json.serialize.impl;


import com.app.extension.json.serializer.CarSerializerExtension;
import com.app.data_provider.DataProvider;
import com.app.json.serialiaze.impl.CarSerializer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.nio.file.Files;
import java.nio.file.Paths;

@ExtendWith(CarSerializerExtension.class)
@RequiredArgsConstructor
public class CarSerializerTest {
    private final CarSerializer carSerializer;

    @Test
    @DisplayName("When data is null")
    public void test1(){
        Assertions.assertThatThrownBy(
                () -> carSerializer.serializer(null, DataProvider.FILE_NAMES_DATA.get("test_save")
                )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Data is null");
    }

    @Test
    @DisplayName("When the filename is null")
    public void test2(){
        Assertions.assertThatThrownBy(
                        () -> carSerializer.serializer(
                                DataProvider.CAR_DATA.get("car1"), null)
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Filename is null");
    }

    @Test
    @DisplayName("When the filename is empty")
    public void test3(){
        Assertions.assertThatThrownBy(
                        () -> carSerializer.serializer(
                                DataProvider.CAR_DATA.get("car1"), "")
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Filename is empty");
    }

    @Test
    @DisplayName("When serialized data creates a file")
    public void test4(){

        carSerializer.serializer(
                DataProvider.CAR_DATA.get("car1"), DataProvider.FILE_NAMES_DATA.get("test_save"));

        Assertions
                .assertThat(
                        Files.exists(Paths.get(DataProvider.FILE_NAMES_DATA.get("test_save"))))
                .isTrue();
    }

    @AfterAll
    @DisplayName("Delete data")
    @SneakyThrows
    public static void deleteData(){
        Files.deleteIfExists(Paths.get(DataProvider.FILE_NAMES_DATA.get("test_save")));
    }

}
