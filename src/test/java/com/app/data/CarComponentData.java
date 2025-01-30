package com.app.data;

import com.app.color.Color;
import com.app.controller.dto.car.CarDto;
import com.app.controller.dto.car.CreateCarDto;
import com.app.controller.dto.components.ComponentDto;
import com.app.controller.dto.components.CreateComponentDto;
import com.app.persistence.entity.CarEntity;
import com.app.persistence.entity.ComponentEntity;

import java.math.BigDecimal;
import java.util.List;

public interface CarComponentData {

    ComponentEntity COMPONENT_ENTITY_READ_1 =
            ComponentEntity.builder().id(1L).name("AIR CONDITION").build();

    ComponentEntity COMPONENT_ENTITY_READ_2 =
            ComponentEntity.builder().id(2L).name("SUNROOF").build();

    ComponentEntity COMPONENT_ENTITY_READ_3 =
            ComponentEntity.builder().id(3L).name("RADIO").build();

    ComponentEntity COMPONENT_ENTITY_READ_4 =
            ComponentEntity.builder().id(4L).name("AUTOMATIC EMERGENCY BRAKING").build();

    ComponentEntity COMPONENT_ENTITY_READ_5 =
            ComponentEntity.builder().id(5L).name("BACKUP CAMERA").build();

    CreateComponentDto CREATE_COMPONENT_DTO_1 = new CreateComponentDto("AIR CONDITION");

    CreateComponentDto CREATE_COMPONENT_DTO_2 = new CreateComponentDto("SUNROOF");

    CreateComponentDto CREATE_COMPONENT_DTO_3 = new CreateComponentDto("RADIO");

    ComponentDto COMPONENT_DTO_1 = new ComponentDto(1L, "AIR CONDITION");

    ComponentDto COMPONENT_DTO_2 = new ComponentDto(2L, "SUNROOF");

    CarEntity CAR_ENTITY_READ_1 = CarEntity
            .builder()
            .id(1L)
            .brand("BMW")
            .model("X3")
            .price(BigDecimal.valueOf(250000))
            .speed(250)
            .color(Color.BLACK)
            .components(List.of(COMPONENT_ENTITY_READ_1, COMPONENT_ENTITY_READ_3))
            .build();

    CarEntity CAR_ENTITY_READ_2 = CarEntity
            .builder()
            .id(2L)
            .brand("BMW")
            .model("X1")
            .price(BigDecimal.valueOf(200000))
            .speed(220)
            .color(Color.RED)
            .components(List.of(COMPONENT_ENTITY_READ_2, COMPONENT_ENTITY_READ_3))
            .build();

    CarEntity CAR_ENTITY_READ_3 = CarEntity
            .builder()
            .id(3L)
            .brand("AUDI")
            .model("A1")
            .price(BigDecimal.valueOf(150000))
            .speed(180)
            .color(Color.BLACK)
            .components(List.of(COMPONENT_ENTITY_READ_5))
            .build();

    CarDto CAR_DTO_1 = new CarDto(
            1L, "BMW", "X3", 250, BigDecimal.valueOf(250000), Color.BLACK, List.of("AIR CONDITION", "RADIO"));

    CarDto CAR_DTO_2 = new CarDto(
            2L, "BMW", "X1", 220, BigDecimal.valueOf(200000), Color.RED, List.of("SUNROOF", "RADIO"));

    CarDto CAR_DTO_3 = new CarDto(
            3L, "AUDI", "A1", 180, BigDecimal.valueOf(150000), Color.BLACK, List.of("BACKUP CAMERA"));

    CreateCarDto CREATE_CAR_DTO_1 = new CreateCarDto(
            "BMW", "X3", 250, BigDecimal.valueOf(250000), Color.BLACK, List.of(1L, 2L));

    CreateCarDto CREATE_CAR_DTO_2 = new CreateCarDto(
            "BMW", "X1", 220, BigDecimal.valueOf(200000), Color.RED, List.of(3L));

    CreateCarDto CREATE_CAR_DTO_3 = new CreateCarDto(
            "AUDI", "A1", 180, BigDecimal.valueOf(150000), Color.BLACK, List.of(3L, 4L));


}
