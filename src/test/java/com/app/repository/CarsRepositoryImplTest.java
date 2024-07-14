package com.app.repository;


import com.app.data_provider.DataProvider;
import com.app.extension.repository.CarsRepositoryImplExtension;
import com.app.repository.impl.CarsRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(CarsRepositoryImplExtension.class)
@RequiredArgsConstructor
public class CarsRepositoryImplTest {

    private final CarsRepositoryImpl carsRepository;

    @Test
    @DisplayName("When has correct data")
    public void test1(){
        Assertions.assertThat(carsRepository.getAll())
                .isEqualTo(DataProvider.CARS_DATA.get("cars1"));
    }

}
