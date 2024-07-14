package com.app.json.deserializer.impl;


import com.app.data_provider.DataProvider;
import com.app.extension.json.deserializer.CarsDeserializerExtension;
import com.app.json.deserialize.impl.CarsDeserializer;
import com.app.model.Cars;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.nio.file.Paths;
import java.util.Objects;

@ExtendWith(CarsDeserializerExtension.class)
@RequiredArgsConstructor
public class CarsDeserializerTest {
    private final CarsDeserializer carsDeserializer;

    @Test
    @DisplayName("When the filename is null")
    public void test1(){
        Assertions.assertThatThrownBy(
                () -> carsDeserializer.deserialize(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Filename is null");
    }

    @Test
    @DisplayName("When the filename is empty")
    public void test2(){
        Assertions.assertThatThrownBy(
                        () -> carsDeserializer.deserialize(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Filename is empty");
    }

    @Test
    @DisplayName("When the deserialize json file")
    @SneakyThrows
    public void test3(){

        var path = Paths
                .get(Objects.requireNonNull(
                        getClass()
                                .getClassLoader()
                                .getResource(
                                        DataProvider.FILE_NAMES_DATA.get("cars json")))
                        .toURI());

        Assertions.assertThat(carsDeserializer.deserialize(path.toString()))
                .isEqualTo(new Cars(DataProvider.CARS_DATA.get("cars1")));
    }
}
