package com.app.controller;

import com.app.color.Color;
import com.app.controller.dto.ResponseDto;
import com.app.controller.dto.car.*;
import com.app.controller.dto.components.ComponentsWithCarsDto;
import com.app.persistence.CarRepository;
import com.app.persistence.ComponentRepository;
import com.app.persistence.entity.CarEntity;
import com.app.persistence.entity.ComponentEntity;
import com.app.persistence.view.GroupByAndPriceStatisticView;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.app.data.CarComponentData.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private CarRepository carRepository;

    private ComponentEntity component_entity_save_1;
    private ComponentEntity component_entity_save_2;
    private ComponentEntity component_entity_save_3;

    private CarEntity car_entity_save_1;
    private CarEntity car_entity_save_2;

    @BeforeEach
    public void setup() {
        component_entity_save_1 = ComponentEntity.builder().name("AIR CONDITION").build();
        component_entity_save_2 = ComponentEntity.builder().name("RADIO").build();
        component_entity_save_3 = ComponentEntity.builder().name("SUNROOF").build();

        car_entity_save_1 = CarEntity
                .builder()
                .brand("BMW")
                .model("X3")
                .price(BigDecimal.valueOf(250000))
                .speed(250)
                .color(Color.BLACK)
                .build();

        car_entity_save_2 = CarEntity
                .builder()
                .brand("BMW")
                .model("X1")
                .price(BigDecimal.valueOf(200000))
                .speed(220)
                .color(Color.RED)
                .build();


    }

    @Test
    @DisplayName("When creating a car, return the correct status and ID related to the created car.")
    @SneakyThrows
    public void test1() {

        componentRepository.saveAll(List.of(component_entity_save_1, component_entity_save_2));

        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CREATE_CAR_DTO_1)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.data").value(1));
    }

    @Test
    @DisplayName("When creating car with an incorrect model, return a bad request status and a message with the exception.")
    @SneakyThrows
    public void test2() {

        componentRepository.saveAll(List.of(component_entity_save_1, component_entity_save_2));

        var incorrectCarDto = new CreateCarDto("BMW", "X1?", 120, BigDecimal.valueOf(190000), Color.BLACK, List.of(1l, 2l));

        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incorrectCarDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.error").value("The model does not match the pattern: [A-Z0-9 ]+."));
    }

    @Test
    @DisplayName("When creating a cars, return the correct status and IDs related to the created cars.")
    @SneakyThrows
    public void test3() {

        componentRepository.saveAll(List.of(component_entity_save_1, component_entity_save_2, component_entity_save_3));

        mockMvc.perform(post("/cars/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(CREATE_CAR_DTO_1, CREATE_CAR_DTO_2))))
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.data").value(containsInAnyOrder(1, 2)));
    }

    @Test
    @DisplayName("When finding a car by ID, return the found status and the car related to the ID.")
    @SneakyThrows
    public void test4() {

        var components = componentRepository.saveAll(List.of(component_entity_save_1, component_entity_save_2));
        carRepository.save(car_entity_save_1.withComponents(components));

        mockMvc.perform(get("/cars/1"))
                .andExpect(status().isFound())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(new ResponseDto<>(CAR_DTO_1))));
    }

    @Test
    @DisplayName("When finding a cars by IDs, return the found status and the cars related to the ID.")
    @SneakyThrows
    public void test5() {

        var components = componentRepository.saveAll(List.of(component_entity_save_1, component_entity_save_2, component_entity_save_3));
        carRepository.saveAll(List.of(
                car_entity_save_1.withComponents(components.subList(0, 2)),
                car_entity_save_2.withComponents(components.subList(1, 3))
        ));

        mockMvc.perform(get("/cars")
                        .queryParam("ids", "1", "2"))
                .andExpect(status().isFound())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(
                        new ResponseDto<>(List.of(CAR_DTO_1, CAR_DTO_2)))));
    }

    @Test
    @DisplayName("When deleting a car by ID, return the ok status and the ID related to the car.")
    @SneakyThrows
    public void test6() {

        var components = componentRepository.saveAll(List.of(component_entity_save_1, component_entity_save_2));
        carRepository.save(car_entity_save_1.withComponents(components));

        mockMvc.perform(delete("/cars/1"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.data").value(1));
    }

    @Test
    @DisplayName("When deleting a cars by IDs, return the ok status and the IDs related to the cars.")
    @SneakyThrows
    public void test7() {

        var components = componentRepository.saveAll(List.of(component_entity_save_1, component_entity_save_2, component_entity_save_3));
        carRepository.saveAll(List.of(
                car_entity_save_1.withComponents(components.subList(0, 2)),
                car_entity_save_2.withComponents(components.subList(1, 3))
        ));

        mockMvc.perform(delete("/cars")
                        .queryParam("ids", "1", "2"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.data").value(containsInAnyOrder(1, 2)));
    }

    @Test
    @DisplayName("When deleting cars by IDs without a query parameter, " +
            "return the internal server error status and a message related to the exception.")
    @SneakyThrows
    public void test8() {

        var components = componentRepository.saveAll(List.of(component_entity_save_1, component_entity_save_2, component_entity_save_3));
        carRepository.saveAll(List.of(
                car_entity_save_1.withComponents(components.subList(0, 2)),
                car_entity_save_2.withComponents(components.subList(1, 3))
        ));

        mockMvc.perform(delete("/cars"))
                .andExpect(status().isInternalServerError())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.error")
                        .value("Required request parameter 'ids' for method parameter type List is not present"));
    }

    @Test
    @DisplayName("When getting sorted cars with a parameter and direction, return cars related to the parameter and direction.")
    @SneakyThrows
    public void test9() {

        var components = componentRepository.saveAll(List.of(component_entity_save_1, component_entity_save_2, component_entity_save_3));
        carRepository.saveAll(List.of(
                car_entity_save_1.withComponents(components.subList(0, 2)),
                car_entity_save_2.withComponents(components.subList(1, 3))
        ));

        mockMvc.perform(get("/cars/sort")
                        .queryParam("parameters", "price")
                        .queryParam("direction", "desc"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(new ResponseDto<>(List.of(CAR_DTO_1, CAR_DTO_2)))));
    }

    @Test
    @DisplayName("When getting cars with speed interval, return cars related to the speed interval.")
    @SneakyThrows
    public void test10() {

        var components = componentRepository.saveAll(List.of(component_entity_save_1, component_entity_save_2, component_entity_save_3));
        carRepository.saveAll(List.of(
                car_entity_save_1.withComponents(components.subList(0, 2)),
                car_entity_save_2.withComponents(components.subList(1, 3))
        ));

        mockMvc.perform(get("/cars/240/300")
                        .queryParam("parameters", "price")
                        .queryParam("direction", "desc"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(new ResponseDto<>(List.of(CAR_DTO_1)))));
    }

    @Test
    @DisplayName("When getting cars with a filter, return cars matching the specified filter.")
    @SneakyThrows
    public void test11() {

        var components = componentRepository.saveAll(List.of(component_entity_save_1, component_entity_save_2, component_entity_save_3));
        carRepository.saveAll(List.of(
                car_entity_save_1.withComponents(components.subList(0, 2)),
                car_entity_save_2.withComponents(components.subList(1, 3))
        ));

        var carCriterionDto = new CarCriterionDto(
                null, null,
                null, null,
                null, null, null, List.of("AIR CONDITION"));

        mockMvc.perform(post("/cars/filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carCriterionDto)))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(new ResponseDto<>(List.of(CAR_DTO_1)))));
    }

    @Test
    @DisplayName("When getting cars mapped by color, return cars grouped by color with the number of cars.")
    @SneakyThrows
    public void test12() {

        var components = componentRepository.saveAll(List.of(component_entity_save_1, component_entity_save_2, component_entity_save_3));
        carRepository.saveAll(List.of(
                car_entity_save_1.withComponents(components.subList(0, 2)),
                car_entity_save_2.withComponents(components.subList(1, 3))
        ));

        mockMvc.perform(get("/cars/color")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(new ResponseDto<>(List.of(
                        new GroupByDto("BLACK", 1L),
                        new GroupByDto("RED", 1L))))
                ));
    }

    @Test
    @DisplayName("When getting cars mapped by model, return cars grouped by model with the number of cars.")
    @SneakyThrows
    public void test13() {

        var components = componentRepository.saveAll(List.of(component_entity_save_1, component_entity_save_2, component_entity_save_3));
        carRepository.saveAll(List.of(
                car_entity_save_1.withComponents(components.subList(0, 2)),
                car_entity_save_2.withComponents(components.subList(1, 3))
        ));

        mockMvc.perform(get("/cars/group/model")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(new ResponseDto<>(List.of(
                        new GroupByDto("X1", 1L),
                        new GroupByDto("X3", 1L))))
                ));
    }

    @Test
    @DisplayName("When getting cars mapped by brand and price statistics, return cars grouped by brand with price statistics.")
    @SneakyThrows
    public void test14() {

        var components = componentRepository.saveAll(List.of(component_entity_save_1, component_entity_save_2, component_entity_save_3));
        carRepository.saveAll(List.of(
                car_entity_save_1.withComponents(components.subList(0, 2)),
                car_entity_save_2.withComponents(components.subList(1, 3))
        ));

        mockMvc.perform(get("/cars/statistic/price")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(new ResponseDto<>(List.of(
                        new GroupByAndPriceStatisticView<>(
                                "BMW",
                                BigDecimal.valueOf(200000).setScale(2, RoundingMode.DOWN),
                                BigDecimal.valueOf(250000).setScale(2, RoundingMode.DOWN)))))
                ));
    }

    @Test
    @DisplayName("When getting cars mapped by model and price statistics, return cars grouped by model with price statistics.")
    @SneakyThrows
    public void test15() {

        var components = componentRepository.saveAll(List.of(component_entity_save_1, component_entity_save_2, component_entity_save_3));
        carRepository.saveAll(List.of(
                car_entity_save_1.withComponents(components.subList(0, 2)),
                car_entity_save_2.withComponents(components.subList(1, 3))
        ));

        mockMvc.perform(get("/cars/price/model")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(new ResponseDto<>(List.of(
                        new GroupByAndPriceStatisticView<>(
                                "X3",
                                BigDecimal.valueOf(250000).setScale(2, RoundingMode.DOWN),
                                BigDecimal.valueOf(250000).setScale(2, RoundingMode.DOWN)),
                        new GroupByAndPriceStatisticView<>(
                                "X1",
                                BigDecimal.valueOf(200000).setScale(2, RoundingMode.DOWN),
                                BigDecimal.valueOf(200000).setScale(2, RoundingMode.DOWN)))))
                ));
    }

    @Test
    @DisplayName("When getting price and speed statistics, return the statistics with price and speed.")
    @SneakyThrows
    public void test16() {

        var components = componentRepository.saveAll(List.of(component_entity_save_1, component_entity_save_2, component_entity_save_3));
        carRepository.saveAll(List.of(
                car_entity_save_1.withComponents(components.subList(0, 2)),
                car_entity_save_2.withComponents(components.subList(1, 3))
        ));

        mockMvc.perform(get("/cars/price-speed")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(new ResponseDto<>(
                        new PriceSpeedStatisticDto(
                                BigDecimal.valueOf(200000).setScale(2, RoundingMode.DOWN),
                                BigDecimal.valueOf(250000).setScale(2, RoundingMode.DOWN),
                                225000.0, 220, 250, 235.0)
                ))));
    }

    @Test
    @DisplayName("When getting cars with sorted components, return the cars in the specified order.")
    @SneakyThrows
    public void test17() {

        var components = componentRepository.saveAll(List.of(component_entity_save_1, component_entity_save_2, component_entity_save_3));
        carRepository.saveAll(List.of(
                car_entity_save_1.withComponents(components.subList(0, 2)),
                car_entity_save_2.withComponents(components.subList(1, 3))
        ));

        mockMvc.perform(get("/cars/sort/components/asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(new ResponseDto<>(
                        List.of(CAR_DTO_1, CAR_DTO_2)))));
    }

    @Test
    @DisplayName("When getting cars grouped by components, ordered by the number of cars, " +
            "return the cars grouped by components in a specific order.")
    @SneakyThrows
    public void test18() {

        var components = componentRepository.saveAll(List.of(component_entity_save_1, component_entity_save_2, component_entity_save_3));
        carRepository.saveAll(List.of(
                car_entity_save_1.withComponents(components.subList(0, 2)),
                car_entity_save_2.withComponents(components.subList(1, 3))
        ));

        mockMvc.perform(get("/cars/components/asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(new ResponseDto<>(
                        List.of(
                                new ComponentsWithCarsDto(
                                        "SUNROOF", List.of(new CarWithoutComponentsDto(
                                        2l, "BMW", "X1", 220, BigDecimal.valueOf(200000), Color.RED))),
                                new ComponentsWithCarsDto(
                                        "AIR CONDITION", List.of(new CarWithoutComponentsDto(
                                        1l, "BMW", "X3", 250, BigDecimal.valueOf(250000), Color.BLACK))),
                                new ComponentsWithCarsDto(
                                        "RADIO", List.of(new CarWithoutComponentsDto(
                                                1l, "BMW", "X3", 250, BigDecimal.valueOf(250000), Color.BLACK),
                                        new CarWithoutComponentsDto(
                                                2l, "BMW", "X1", 220, BigDecimal.valueOf(200000), Color.RED))
                                )
                        )))));
    }

    @Test
    @DisplayName("When getting cars near a specific price, return cars close to that price.")
    @SneakyThrows
    public void test19() {

        var components = componentRepository.saveAll(List.of(component_entity_save_1, component_entity_save_2, component_entity_save_3));
        carRepository.saveAll(List.of(
                car_entity_save_1.withComponents(components.subList(0, 2)),
                car_entity_save_2.withComponents(components.subList(1, 3))
        ));

        mockMvc.perform(get("/cars/close/210000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(new ResponseDto<>(List.of(CAR_DTO_2)))));
    }
}
