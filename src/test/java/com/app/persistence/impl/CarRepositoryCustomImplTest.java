package com.app.persistence.impl;

import com.app.color.Color;
import com.app.persistence.CarRepository;
import com.app.persistence.ComponentRepository;
import com.app.persistence.entity.CarEntity;
import com.app.persistence.entity.ComponentEntity;
import com.app.persistence.view.GroupByAndPriceStatisticView;
import com.app.persistence.view.GroupByView;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class CarRepositoryCustomImplTest {

    @Autowired
    private CarRepositoryCustomImpl carRepositoryCustom;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ComponentRepository componentRepository;

    private ComponentEntity COMPONENT_ENTITY_SAVE_1;

    private ComponentEntity COMPONENT_ENTITY_SAVE_2;

    private ComponentEntity COMPONENT_ENTITY_SAVE_3;

    private ComponentEntity COMPONENT_ENTITY_SAVE_4;

    private ComponentEntity COMPONENT_ENTITY_SAVE_5;

    private CarEntity CAR_ENTITY_SAVE_1;
    private CarEntity CAR_ENTITY_SAVE_2;
    private CarEntity CAR_ENTITY_SAVE_3;

    @BeforeEach
    public void setUp() {
        COMPONENT_ENTITY_SAVE_1 =
                ComponentEntity.builder().name("AIR CONDITION").build();

        COMPONENT_ENTITY_SAVE_2 =
                ComponentEntity.builder().name("SUNROOF").build();

        COMPONENT_ENTITY_SAVE_3 =
                ComponentEntity.builder().name("RADIO").build();

        COMPONENT_ENTITY_SAVE_4 =
                ComponentEntity.builder().name("AUTOMATIC EMERGENCY BRAKING").build();

        COMPONENT_ENTITY_SAVE_5 =
                ComponentEntity.builder().name("BACKUP CAMERA").build();

        CAR_ENTITY_SAVE_1 = CarEntity
                .builder()
                .brand("BMW")
                .model("X3")
                .price(BigDecimal.valueOf(250000))
                .speed(250)
                .color(Color.BLACK)
                .build();

        CAR_ENTITY_SAVE_2 = CarEntity
                .builder()
                .brand("BMW")
                .model("X1")
                .price(BigDecimal.valueOf(200000))
                .speed(220)
                .color(Color.RED)
                .build();

        CAR_ENTITY_SAVE_3 = CarEntity
                .builder()
                .brand("AUDI")
                .model("A1")
                .price(BigDecimal.valueOf(150000))
                .speed(180)
                .color(Color.BLACK)
                .build();

        var components = componentRepository.saveAll(List.of(
                COMPONENT_ENTITY_SAVE_1,
                COMPONENT_ENTITY_SAVE_2,
                COMPONENT_ENTITY_SAVE_3,
                COMPONENT_ENTITY_SAVE_4,
                COMPONENT_ENTITY_SAVE_5));

        carRepository.saveAll(List.of(
                CAR_ENTITY_SAVE_1.withComponents(List.of(components.get(0))),
                CAR_ENTITY_SAVE_2.withComponents(List.of(components.get(2), components.get(4))),
                CAR_ENTITY_SAVE_3.withComponents(List.of(components.get(1), components.get(2)))));
    }

    @Test
    @DisplayName("When we group by brand and return a list of brands and the number of cars.")
    public void test1() {

        Assertions.assertThat(carRepositoryCustom.groupByField("brand"))
                .containsExactlyInAnyOrder(new GroupByView<>("AUDI", 1l), new GroupByView<>("BMW", 2l));
    }

    @Test
    @DisplayName("When we group by color and return a list of color and the number of cars.")
    public void test2() {

        Assertions.assertThat(carRepositoryCustom.groupByField("color"))
                .containsExactlyInAnyOrder(new GroupByView<>(Color.RED, 1l), new GroupByView<>(Color.BLACK, 2l));
    }

    @Test
    @DisplayName("When we group by color and return a list of color and the price statistic.")
    public void test3() {

        Assertions.assertThat(carRepositoryCustom.groupByAndPriceStatisticField("color"))
                .containsExactlyInAnyOrder(
                        new GroupByAndPriceStatisticView<>(
                                Color.RED,
                                BigDecimal.valueOf(200000).setScale(2, RoundingMode.DOWN),
                                BigDecimal.valueOf(200000).setScale(2, RoundingMode.DOWN)),
                        new GroupByAndPriceStatisticView<>(
                                Color.BLACK,
                                BigDecimal.valueOf(150000).setScale(2, RoundingMode.DOWN),
                                BigDecimal.valueOf(250000).setScale(2, RoundingMode.DOWN)));
    }

    @Test
    @DisplayName("When we group by brand and return a list of color and the price statistic.")
    public void test4() {

        Assertions.assertThat(carRepositoryCustom.groupByAndPriceStatisticField("brand"))
                .containsExactlyInAnyOrder(
                        new GroupByAndPriceStatisticView<>(
                                "AUDI",
                                BigDecimal.valueOf(150000).setScale(2, RoundingMode.DOWN),
                                BigDecimal.valueOf(150000).setScale(2, RoundingMode.DOWN)),
                        new GroupByAndPriceStatisticView<>(
                                "BMW",
                                BigDecimal.valueOf(200000).setScale(2, RoundingMode.DOWN),
                                BigDecimal.valueOf(250000).setScale(2, RoundingMode.DOWN)));
    }
}
