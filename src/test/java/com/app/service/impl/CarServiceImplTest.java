package com.app.service.impl;

import com.app.color.Color;
import com.app.controller.dto.car.*;
import com.app.controller.dto.components.ComponentsWithCarsDto;
import com.app.persistence.CarRepository;
import com.app.persistence.ComponentRepository;
import com.app.persistence.CrudRepository;
import com.app.persistence.entity.CarEntity;
import com.app.persistence.impl.CarSpecificationImpl;
import com.app.persistence.view.*;
import com.app.validate.Validator;
import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.app.data.CarComponentData.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private CrudRepository<CarEntity, Long> crudRepository;
    @Mock
    private ComponentRepository componentRepository;

    @Mock
    private CarSpecificationImpl carSpecificationImpl;

    @Mock
    private Validator<CreateCarDto> validator;

    @Mock
    private Validator<String> parametersValidator;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    @DisplayName("When finding all cars from the database, return a list of car entities.")
    public void test1() {

        when(carRepository.findAll())
                .thenReturn(List.of(CAR_ENTITY_READ_1, CAR_ENTITY_READ_2, CAR_ENTITY_READ_3));


        Assertions.assertThat(carService.findAll())
                .containsExactlyInAnyOrder(CAR_ENTITY_READ_1, CAR_ENTITY_READ_2, CAR_ENTITY_READ_3);

        verify(carRepository, times(1))
                .findAll();
    }

    @Test
    @DisplayName("When finding car by id from the database, return a car entity.")
    public void test2() {

        when(carRepository.findById(1l))
                .thenReturn(Optional.of(CAR_ENTITY_READ_1));

        Assertions.assertThat(carService.findById(1L))
                .isEqualTo(CAR_ENTITY_READ_1);

        verify(carRepository, times(1))
                .findById(1L);
    }

    @Test
    @DisplayName("When a car with the specified ID is not found in the database, throw an EntityNotFoundException.")
    public void test3() {

        when(carRepository.findById(1l))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> carService.findById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Element not found.");

        verify(carRepository, times(1))
                .findById(1L);
    }

    @Test
    @DisplayName("When finding cars by ids from the database, return a car entities.")
    public void test4() {

        when(carRepository.findAllById(anyList()))
                .thenReturn(List.of(CAR_ENTITY_READ_1, CAR_ENTITY_READ_2));

        Assertions.assertThat(carService.findAllById(List.of(1L, 2L)))
                .contains(CAR_ENTITY_READ_1, CAR_ENTITY_READ_2);

        verify(carRepository, times(1))
                .findAllById(anyList());
    }


    @Test
    @DisplayName("When searching for cars by IDs in the database and not all cars are found, throw an EntityNotFoundException.")
    public void test5() {

        when(carRepository.findAllById(anyList()))
                .thenReturn(List.of(CAR_ENTITY_READ_1));

        Assertions.assertThatThrownBy(() -> carService.findAllById(List.of(1L, 2L)))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Not all elements were found.");

        verify(carRepository, times(1))
                .findAllById(anyList());
    }

    @Test
    @DisplayName("When saving cars to the database, return their IDs if the operation completes successfully.")
    public void test6() {

        doNothing().when(validator)
                .validate(any(CreateCarDto.class));

        when(componentRepository.findAllById(anyList()))
                .thenReturn(List.of(COMPONENT_ENTITY_READ_1, COMPONENT_ENTITY_READ_2, COMPONENT_ENTITY_READ_3, COMPONENT_ENTITY_READ_4));

        when(carRepository.saveAll(anyList()))
                .thenReturn(List.of(CAR_ENTITY_READ_1, CAR_ENTITY_READ_2, CAR_ENTITY_READ_3));

        Assertions.assertThat(carService.saveAll(List.of(CREATE_CAR_DTO_1, CREATE_CAR_DTO_2, CREATE_CAR_DTO_3)))
                .contains(1l, 2l, 3l);

        var inOrder = inOrder(carRepository, componentRepository);

        inOrder.verify(componentRepository, times(1))
                .findAllById(anyList());

        inOrder.verify(carRepository, times(1))
                .saveAll(anyList());
    }

    @Test
    @DisplayName("When saving cars to the database, if not all components are found, throw an EntityNotFoundException.")
    public void test7() {

        doNothing().when(validator)
                .validate(ArgumentMatchers.any());

        when(componentRepository.findAllById(anyList()))
                .thenReturn(List.of(COMPONENT_ENTITY_READ_1, COMPONENT_ENTITY_READ_2, COMPONENT_ENTITY_READ_3));

        Assertions.assertThatThrownBy(() -> carService.saveAll(List.of(CREATE_CAR_DTO_1, CREATE_CAR_DTO_2, CREATE_CAR_DTO_3)))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Not all components were found");

        var inOrder = inOrder(componentRepository);

        inOrder.verify(componentRepository, times(1))
                .findAllById(anyList());
    }

    @Test
    @DisplayName("When saving car to the database, return the ID if the operation completes successfully.")
    public void test8() {

        doNothing().when(validator)
                .validate(ArgumentMatchers.any());

        when(componentRepository.findAllById(anyList()))
                .thenReturn(List.of(COMPONENT_ENTITY_READ_1, COMPONENT_ENTITY_READ_2));

        when(carRepository.save(any()))
                .thenReturn(CAR_ENTITY_READ_1);

        Assertions.assertThat(carService.save(CREATE_CAR_DTO_1))
                .isEqualTo(1l);

        var inOrder = inOrder(carRepository, componentRepository, validator);

        inOrder.verify(componentRepository, times(1))
                .findAllById(anyList());

        inOrder.verify(carRepository, times(1))
                .save(any());
    }

    @Test
    @DisplayName("When saving car to the database, if not all components are found, throw an EntityNotFoundException.")
    public void test9() {

        doNothing().when(validator)
                .validate(ArgumentMatchers.any());

        when(componentRepository.findAllById(anyList()))
                .thenReturn(List.of(COMPONENT_ENTITY_READ_1));

        when(carRepository.save(any()))
                .thenReturn(CAR_ENTITY_READ_1);

        Assertions.assertThatThrownBy(() -> carService.save(CREATE_CAR_DTO_1))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Not all components were found");

        var inOrder = inOrder(componentRepository, validator);

        inOrder.verify(componentRepository, times(1))
                .findAllById(anyList());

    }

    @Test
    @DisplayName("When delete car by id from the database, return the ID if the operation completes successfully.")
    public void test10() {

        when(carRepository.findById(any()))
                .thenReturn(Optional.of(CAR_ENTITY_READ_1));

        doNothing().when(carRepository).delete(any());

        Assertions.assertThat(carService.deleteById(1L))
                .isEqualTo(1L);

        var inOrder = inOrder(carRepository);

        inOrder.verify(carRepository, times(1))
                .findById(anyLong());

        inOrder.verify(carRepository, times(1))
                .delete(any());

    }

    @Test
    @DisplayName("When deleting a non-existing car by ID from the database, throw an EntityNotFoundException.")
    public void test11() {

        when(carRepository.findById(any()))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> carService.deleteById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Element not found.");

        var inOrder = inOrder(carRepository);

        inOrder.verify(carRepository, times(1))
                .findById(anyLong());

    }

    @Test
    @DisplayName("When delete cars by ids from the database, return the IDs if the operation completes successfully.")
    public void test12() {

        when(carRepository.findAllById(anyList()))
                .thenReturn(List.of(CAR_ENTITY_READ_1, CAR_ENTITY_READ_2));

        doNothing().when(carRepository).deleteAll(anyList());

        Assertions.assertThat(carService.deleteAllById(List.of(1L, 2L)))
                .contains(1L, 2L);

        var inOrder = inOrder(carRepository);

        inOrder.verify(carRepository, times(1))
                .findAllById(anyList());

        inOrder.verify(carRepository, times(1))
                .deleteAll(anyList());
    }

    @Test
    @DisplayName("When deleting cars by IDs from the database, if not all cars are found, throw an EntityNotFoundException.")
    public void test13() {

        when(carRepository.findAllById(anyList()))
                .thenReturn(List.of(CAR_ENTITY_READ_1));

        doNothing().when(carRepository).deleteAll(anyList());

        Assertions.assertThatThrownBy(() -> carService.deleteAllById(List.of(1L, 2L)))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Not all elements were found.");

        var inOrder = inOrder(carRepository);

        inOrder.verify(carRepository, times(1))
                .findAllById(anyList());
    }

    @Test
    @DisplayName("When retrieving cars from the database with a sort parameter and direction, return a sorted list of cars.")
    public void test14() {

        doNothing().when(parametersValidator).validate(anyString());

        when(carRepository.findAll(any(Sort.class)))
                .thenReturn(List.of(CAR_ENTITY_READ_1, CAR_ENTITY_READ_2, CAR_ENTITY_READ_3));

        doNothing().when(carRepository).deleteAll(anyList());

        Assertions.assertThat(carService.sortedCarsBy(List.of("model", "brand"), "asc"))
                .contains(CAR_DTO_1, CAR_DTO_2, CAR_DTO_3);

        var inOrder = inOrder(parametersValidator, carRepository);

        inOrder.verify(carRepository, times(1))
                .findAll(any(Sort.class));
    }

    @Test
    @DisplayName("When retrieving cars from the database with a sort parameter and direction, return a sorted list of cars.")
    public void test15() {

        doNothing().when(parametersValidator).validate(anyString());

        when(carRepository.findAll(any(Sort.class)))
                .thenReturn(List.of(CAR_ENTITY_READ_1, CAR_ENTITY_READ_2, CAR_ENTITY_READ_3));

        doNothing().when(carRepository).deleteAll(anyList());

        Assertions.assertThat(carService.sortedCarsBy(List.of("model", "brand"), "desc"))
                .contains(CAR_DTO_1, CAR_DTO_2, CAR_DTO_3);

        var inOrder = inOrder(parametersValidator, carRepository);

        inOrder.verify(carRepository, times(1))
                .findAll(any(Sort.class));
    }

    @Test
    @DisplayName("When retrieving cars from the database with a sort parameter and direction, if the sort parameter is null, throw an IllegalArgumentException.")
    public void test16() {

        Assertions.assertThatThrownBy(() -> carService.sortedCarsBy(null, "asc"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Parameters cannot be null");
    }

    @Test
    @DisplayName("When retrieving cars from the database with a sort parameter and direction, if the sort parameter is empty, throw an IllegalArgumentException.")
    public void test17() {

        Assertions.assertThatThrownBy(() -> carService.sortedCarsBy(List.of(), "asc"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Parameters cannot be empty");
    }

    @Test
    @DisplayName("When retrieving cars from the database with a sort parameter and direction, if the direction is null, throw an IllegalArgumentException.")
    public void test18() {

        Assertions.assertThatThrownBy(() -> carService.sortedCarsBy(List.of("model", "brand"), null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Direction cannot be null");
    }

    @Test
    @DisplayName("When retrieving cars from the database with a sort parameter and direction, if the direction is incorrect, throw an IllegalArgumentException.")
    public void test19() {

        Assertions.assertThatThrownBy(() -> carService.sortedCarsBy(List.of("model", "brand"), "natural"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Direction must be 'asc' or 'desc'.");
    }


    @Test
    @DisplayName("When retrieving cars from the database with a speed interval, return cars related to the specified speed range.")
    public void test20() {

        when(carRepository.findCarEntitiesBySpeedBetween(200, 300))
                .thenReturn(List.of(CAR_ENTITY_READ_1, CAR_ENTITY_READ_2));

        Assertions.assertThat(carService.getCarsWithSpeedInterval(200, 300))
                .contains(CAR_DTO_1, CAR_DTO_2);


        verify(carRepository, times(1))
                .findCarEntitiesBySpeedBetween(200, 300);
    }

    @Test
    @DisplayName("When retrieving cars from the database with a speed interval, if the minimum speed is negative, throw an IllegalArgumentException.")
    public void test21() {

        Assertions.assertThatThrownBy(() -> carService.getCarsWithSpeedInterval(-100, 300))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Min speed cannot be negative");
    }

    @Test
    @DisplayName("When retrieving cars from the database with a speed interval, if the minimum speed is greater than maximum speed, throw an IllegalArgumentException.")
    public void test22() {

        Assertions.assertThatThrownBy(() -> carService.getCarsWithSpeedInterval(200, 100))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Min speed cannot be greater than max speed");
    }

    @Test
    @DisplayName("When retrieving cars from the database with filtering, return cars related to the specified filter.")
    public void test23() {

        when(carRepository.findAll(any(Specification.class)))
                .thenReturn(List.of(CAR_ENTITY_READ_1, CAR_ENTITY_READ_2));

        when(carSpecificationImpl.dynamicFilters(any(CarCriterionFilteringView.class)))
                .thenCallRealMethod();

        Assertions.assertThat(carService.getCarsFilterBy(new CarCriterionDto(
                        "bmw", null, null, null, null, null, null, null)))
                .contains(CAR_DTO_1, CAR_DTO_2);

        verify(carRepository, times(1))
                .findAll(any(Specification.class));

    }

    @Test
    @DisplayName("When retrieving cars from the database with filtering, if carCriterionDto is null, throw an IllegalArgumentException.")
    public void test24() {


        when(carSpecificationImpl.dynamicFilters(any(CarCriterionFilteringView.class)))
                .thenCallRealMethod();

        Assertions.assertThatThrownBy(() -> carService.getCarsFilterBy(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Criterion cannot be null");

    }

    @Test
    @DisplayName("When retrieving cars grouped by a parameter, return the data grouped accordingly.")
    public void test25() {

        doNothing().when(parametersValidator).validate(anyString());

        when(carRepository.groupByField(anyString()))
                .thenReturn(List.of(new GroupByView<>("BMW", 2L), new GroupByView<>("AUDI", 1L)));

        Assertions.assertThat(carService.groupByAndAmountOfCars("brand"))
                .contains(new GroupByDto<>("BMW", 2L), new GroupByDto<>("AUDI", 1L));

        verify(carRepository, times(1)).groupByField(anyString());
    }

    @Test
    @DisplayName("When retrieving cars grouped by a parameter and price statistic, return the data grouped accordingly.")
    public void test26() {

        doNothing().when(parametersValidator).validate(anyString());

        when(carRepository.groupByAndPriceStatisticField(anyString()))
                .thenReturn(List.of(
                        new GroupByAndPriceStatisticView<>("BMW", BigDecimal.valueOf(200000), BigDecimal.valueOf(250000)),
                        new GroupByAndPriceStatisticView<>("AUDI", BigDecimal.valueOf(150000), BigDecimal.valueOf(150000))));

        Assertions.assertThat(carService.groupByAndMinMaxPriceStatistic("brand"))
                .contains(
                        new GroupByAndPriceStatisticDto<>("BMW", BigDecimal.valueOf(200000), BigDecimal.valueOf(250000)),
                        new GroupByAndPriceStatisticDto<>("AUDI", BigDecimal.valueOf(150000), BigDecimal.valueOf(150000))
                );

        verify(carRepository, times(1)).groupByAndPriceStatisticField(anyString());
    }

    @Test
    @DisplayName("When retrieving price and speed statistics from the database, return the corresponding price and speed statistics")
    public void test27() {

        doNothing().when(parametersValidator).validate(anyString());

        when(carRepository.findPriceSpeedStatistics())
                .thenReturn(Optional.of(new PriceSpeedStatisticView(
                        BigDecimal.valueOf(100000),
                        BigDecimal.valueOf(200000),
                        150000.0,
                        100,
                        200,
                        150.0))
                );

        Assertions.assertThat(carService.priceSpeedStatistic())
                .isEqualTo(new PriceSpeedStatisticDto(BigDecimal.valueOf(100000),
                        BigDecimal.valueOf(200000),
                        150000.0,
                        100,
                        200,
                        150.0));

        verify(carRepository, times(1)).findPriceSpeedStatistics();
    }

    @Test
    @DisplayName("When retrieving price and speed statistics from the database and statistic not found, throw an EntityNotFoundException.")
    public void test28() {

        when(carRepository.findPriceSpeedStatistics())
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> carService.priceSpeedStatistic())
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Not found price and speed statistic");

        verify(carRepository, times(1)).findPriceSpeedStatistics();
    }

    @Test
    @DisplayName("When retrieving cars with components sorted in natural order, return the cars with their components sorted accordingly.")
    public void test29() {

        var componentsDto1 = new CarDto(CAR_DTO_1.id(), CAR_DTO_1.brand(), CAR_DTO_1.model(), CAR_DTO_1.speed(), CAR_DTO_1.price(), CAR_DTO_1.color(),
                CAR_DTO_1.components().stream().sorted(Comparator.naturalOrder()).toList());

        var componentsDto2 = new CarDto(CAR_DTO_2.id(), CAR_DTO_2.brand(), CAR_DTO_2.model(), CAR_DTO_2.speed(), CAR_DTO_2.price(), CAR_DTO_2.color(),
                CAR_DTO_2.components().stream().sorted(Comparator.naturalOrder()).toList());

        var componentsDto3 = new CarDto(CAR_DTO_3.id(), CAR_DTO_3.brand(), CAR_DTO_3.model(), CAR_DTO_3.speed(), CAR_DTO_3.price(), CAR_DTO_3.color(),
                CAR_DTO_3.components().stream().sorted(Comparator.naturalOrder()).toList());
        when(carRepository.findAll())
                .thenReturn(List.of(CAR_ENTITY_READ_1, CAR_ENTITY_READ_2, CAR_ENTITY_READ_3));

        Assertions.assertThat(carService.getCarsWithSortedComponents("asc"))
                .contains(componentsDto1, componentsDto2, componentsDto3);

        verify(carRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("When retrieving cars with components sorted in reverse order, return the cars with their components sorted accordingly.")
    public void test30() {

        var componentsDto1 = new CarDto(CAR_DTO_1.id(), CAR_DTO_1.brand(), CAR_DTO_1.model(), CAR_DTO_1.speed(), CAR_DTO_1.price(), CAR_DTO_1.color(),
                CAR_DTO_1.components().stream().sorted(Comparator.reverseOrder()).toList());

        var componentsDto2 = new CarDto(CAR_DTO_2.id(), CAR_DTO_2.brand(), CAR_DTO_2.model(), CAR_DTO_2.speed(), CAR_DTO_2.price(), CAR_DTO_2.color(),
                CAR_DTO_2.components().stream().sorted(Comparator.reverseOrder()).toList());

        var componentsDto3 = new CarDto(CAR_DTO_3.id(), CAR_DTO_3.brand(), CAR_DTO_3.model(), CAR_DTO_3.speed(), CAR_DTO_3.price(), CAR_DTO_3.color(),
                CAR_DTO_3.components().stream().sorted(Comparator.reverseOrder()).toList());
        when(carRepository.findAll())
                .thenReturn(List.of(CAR_ENTITY_READ_1, CAR_ENTITY_READ_2, CAR_ENTITY_READ_3));

        Assertions.assertThat(carService.getCarsWithSortedComponents("desc"))
                .contains(componentsDto1, componentsDto2, componentsDto3);

        verify(carRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("When retrieving cars with components sorted in reverse order, if order is null, throw an IllegalArgumentException.")
    public void test31() {

        Assertions.assertThatThrownBy(() -> carService.getCarsWithSortedComponents(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Order cannot be null");
    }

    @Test
    @DisplayName("When retrieving cars with components sorted in reverse order, if order is incorrect, throw an IllegalArgumentException.")
    public void test32() {

        Assertions.assertThatThrownBy(() -> carService.getCarsWithSortedComponents("natural"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid order: " + "natural");
    }

    @Test
    @DisplayName("When retrieving cars grouped by components and sorted by the number of cars in natural order, " +
            "return the grouped cars with their components sorted accordingly.")
    public void test33() {

        when(componentRepository.groupByComponent())
                .thenReturn(List.of(new ComponentsAndCarsView(
                                "AIR CONDITION", 1L, "BMW", "X3", Color.BLACK, BigDecimal.valueOf(250000), 250),
                        new ComponentsAndCarsView(
                                "RADIO", 1L, "BMW", "X3", Color.BLACK, BigDecimal.valueOf(250000), 250),
                        new ComponentsAndCarsView(
                                "SUNROOF", 3L, "AUDI", "A1", Color.RED, BigDecimal.valueOf(150000), 180),
                        new ComponentsAndCarsView(
                                "RADIO", 2L, "BMW", "X1", Color.BLACK, BigDecimal.valueOf(200000), 220)
                ));

        Assertions.assertThat(carService.groupByComponentsAndCarsSortedByAmountOfCars("asc"))
                .contains(
                        new ComponentsWithCarsDto(
                                "RADIO", List.of(
                                new CarWithoutComponentsDto(1L, "BMW", "X3", 250, BigDecimal.valueOf(250000), Color.BLACK),
                                new CarWithoutComponentsDto(2L, "BMW", "X1", 220, BigDecimal.valueOf(200000), Color.BLACK)
                        )),
                        new ComponentsWithCarsDto(
                                "AIR CONDITION", List.of(
                                new CarWithoutComponentsDto(1L, "BMW", "X3", 250, BigDecimal.valueOf(250000), Color.BLACK)
                        )),
                        new ComponentsWithCarsDto(
                                "SUNROOF", List.of(
                                new CarWithoutComponentsDto(3L, "AUDI", "A1", 180, BigDecimal.valueOf(150000), Color.RED)
                        ))
                );

        verify(componentRepository, times(1)).groupByComponent();
    }

    @Test
    @DisplayName("When retrieving cars grouped by components and sorted by the number of cars in revers order, return the grouped cars with their components sorted accordingly.")
    public void test34() {

        when(componentRepository.groupByComponent())
                .thenReturn(List.of(new ComponentsAndCarsView(
                                "AIR CONDITION", 1L, "BMW", "X3", Color.BLACK, BigDecimal.valueOf(250000), 250),
                        new ComponentsAndCarsView(
                                "RADIO", 1L, "BMW", "X3", Color.BLACK, BigDecimal.valueOf(250000), 250),
                        new ComponentsAndCarsView(
                                "SUNROOF", 3L, "AUDI", "A1", Color.RED, BigDecimal.valueOf(150000), 180),
                        new ComponentsAndCarsView(
                                "RADIO", 2L, "BMW", "X1", Color.BLACK, BigDecimal.valueOf(200000), 220)
                ));

        Assertions.assertThat(carService.groupByComponentsAndCarsSortedByAmountOfCars("desc"))
                .contains(
                        new ComponentsWithCarsDto(
                                "RADIO", List.of(
                                new CarWithoutComponentsDto(1L, "BMW", "X3", 250, BigDecimal.valueOf(250000), Color.BLACK),
                                new CarWithoutComponentsDto(2L, "BMW", "X1", 220, BigDecimal.valueOf(200000), Color.BLACK)
                        )),
                        new ComponentsWithCarsDto(
                                "AIR CONDITION", List.of(
                                new CarWithoutComponentsDto(1L, "BMW", "X3", 250, BigDecimal.valueOf(250000), Color.BLACK)
                        )),
                        new ComponentsWithCarsDto(
                                "SUNROOF", List.of(
                                new CarWithoutComponentsDto(3L, "AUDI", "A1", 180, BigDecimal.valueOf(150000), Color.RED)
                        ))
                );

        verify(componentRepository, times(1)).groupByComponent();
    }

    @Test
    @DisplayName("When retrieving cars grouped by components and sorted by the number of cars, " +
            "if the sort parameter is null, throw an IllegalArgumentException.")
    public void test35() {

        Assertions.assertThatThrownBy(() -> carService.groupByComponentsAndCarsSortedByAmountOfCars(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Order cannot be null");
    }

    @Test
    @DisplayName("When retrieving cars close to a given price, return a list of cars.")
    public void test36() {

        when(carRepository.findCarsCloseToPrice(any(BigDecimal.class)))
                .thenReturn(List.of(CAR_ENTITY_READ_1));

        Assertions.assertThat(carService.getCarsCloseToPrice(BigDecimal.valueOf(2000000)))
                .contains(CAR_DTO_1);

        verify(carRepository, times(1))
                .findCarsCloseToPrice(any(BigDecimal.class));
    }

    @Test
    @DisplayName("When retrieving cars close to a given price, if price is null, throw an IllegalArgumentException.")
    public void test37() {

        Assertions.assertThatThrownBy(() -> carService.getCarsCloseToPrice(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Price cannot be null");
    }

    @Test
    @DisplayName("When retrieving cars close to a given price, if price is negative, throw an IllegalArgumentException.")
    public void test38() {

        Assertions.assertThatThrownBy(() -> carService.getCarsCloseToPrice(BigDecimal.valueOf(-10)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Price cannot be negative");
    }
}
