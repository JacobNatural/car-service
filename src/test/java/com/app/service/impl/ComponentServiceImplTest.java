package com.app.service.impl;


import com.app.controller.dto.components.CreateComponentDto;
import com.app.persistence.ComponentRepository;
import com.app.validate.CreateComponentDtoValidator;
import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.app.data.CarComponentData.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ComponentServiceImplTest {

    @Mock
    private ComponentRepository componentRepository;

    @Mock
    private CreateComponentDtoValidator createComponentDtoValidator;


    @InjectMocks
    private ComponentServiceImpl componentServiceImpl;

    @Test
    @DisplayName("When finding all components from the database, return a list of components entity.")
    public void test1() {

        when(componentRepository.findAll())
                .thenReturn(List.of(COMPONENT_ENTITY_READ_1, COMPONENT_ENTITY_READ_2, COMPONENT_ENTITY_READ_3));


        Assertions.assertThat(componentServiceImpl.findAll())
                .containsExactlyInAnyOrder(COMPONENT_ENTITY_READ_1, COMPONENT_ENTITY_READ_2, COMPONENT_ENTITY_READ_3);

        verify(componentRepository, times(1))
                .findAll();
    }

    @Test
    @DisplayName("When finding component by id from the database, return a car entity.")
    public void test2() {

        when(componentRepository.findById(1l))
                .thenReturn(Optional.of(COMPONENT_ENTITY_READ_1));

        Assertions.assertThat(componentServiceImpl.findById(1L))
                .isEqualTo(COMPONENT_ENTITY_READ_1);

        verify(componentRepository, times(1))
                .findById(1L);
    }

    @Test
    @DisplayName("When a component with the specified ID is not found in the database, throw an EntityNotFoundException.")
    public void test3() {

        when(componentRepository.findById(1l))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> componentServiceImpl.findById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Element not found.");

        verify(componentRepository, times(1))
                .findById(1L);
    }

    @Test
    @DisplayName("When finding components by ids from the database, return a component entities.")
    public void test4() {

        when(componentRepository.findAllById(anyList()))
                .thenReturn(List.of(COMPONENT_ENTITY_READ_1, COMPONENT_ENTITY_READ_2));

        Assertions.assertThat(componentServiceImpl.findAllById(List.of(1L, 2L)))
                .contains(COMPONENT_ENTITY_READ_1, COMPONENT_ENTITY_READ_2);

        verify(componentRepository, times(1))
                .findAllById(anyList());
    }

    @Test
    @DisplayName("When searching for components by IDs in the database and not all components are found, throw an EntityNotFoundException.")
    public void test5() {

        when(componentRepository.findAllById(anyList()))
                .thenReturn(List.of(COMPONENT_ENTITY_READ_1));

        Assertions.assertThatThrownBy(() -> componentServiceImpl.findAllById(List.of(1L, 2L)))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Not all elements were found.");

        verify(componentRepository, times(1))
                .findAllById(anyList());
    }

    @Test
    @DisplayName("When delete component by id from the database, return the ID if the operation completes successfully.")
    public void test6() {

        when(componentRepository.findById(any()))
                .thenReturn(Optional.of(COMPONENT_ENTITY_READ_1));

        doNothing().when(componentRepository).delete(any());

        Assertions.assertThat(componentServiceImpl.deleteById(1L))
                .isEqualTo(1L);

        var inOrder = inOrder(componentRepository);

        inOrder.verify(componentRepository, times(1))
                .findById(anyLong());

        inOrder.verify(componentRepository, times(1))
                .delete(any());

    }

    @Test
    @DisplayName("When deleting a non-existing component by ID from the database, throw an EntityNotFoundException.")
    public void test7() {

        when(componentRepository.findById(any()))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> componentServiceImpl.deleteById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Element not found.");

        var inOrder = inOrder(componentRepository);

        inOrder.verify(componentRepository, times(1))
                .findById(anyLong());

    }

    @Test
    @DisplayName("When delete components by ids from the database, return the IDs if the operation completes successfully.")
    public void test8() {

        when(componentRepository.findAllById(anyList()))
                .thenReturn(List.of(COMPONENT_ENTITY_READ_1, COMPONENT_ENTITY_READ_2));

        doNothing().when(componentRepository).deleteAll(anyList());

        Assertions.assertThat(componentServiceImpl.deleteAllById(List.of(1L, 2L)))
                .contains(1L, 2L);

        var inOrder = inOrder(componentRepository);

        inOrder.verify(componentRepository, times(1))
                .findAllById(anyList());

        inOrder.verify(componentRepository, times(1))
                .deleteAll(anyList());
    }

    @Test
    @DisplayName("When deleting components by IDs from the database, if not all components are found, throw an EntityNotFoundException.")
    public void test9() {

        when(componentRepository.findAllById(anyList()))
                .thenReturn(List.of(COMPONENT_ENTITY_READ_1));

        doNothing().when(componentRepository).deleteAll(anyList());

        Assertions.assertThatThrownBy(() -> componentServiceImpl.deleteAllById(List.of(1L, 2L)))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Not all elements were found.");

        var inOrder = inOrder(componentRepository);

        inOrder.verify(componentRepository, times(1))
                .findAllById(anyList());
    }

    @Test
    @DisplayName("When saving component to the database, return the ID if the operation completes successfully.")
    public void test10() {

        doNothing().when(createComponentDtoValidator)
                .validate(ArgumentMatchers.any());


        when(componentRepository.save(any()))
                .thenReturn(COMPONENT_ENTITY_READ_1);

        Assertions.assertThat(componentServiceImpl.save(CREATE_COMPONENT_DTO_1))
                .isEqualTo(1l);

        var inOrder = inOrder(componentRepository, createComponentDtoValidator);

        inOrder.verify(componentRepository, times(1))
                .save(any());
    }

    @Test
    @DisplayName("When saving components to the database, return their IDs if the operation completes successfully.")
    public void test11() {

        doNothing().when(createComponentDtoValidator)
                .validate(any(CreateComponentDto.class));

        when(componentRepository.saveAll(anyList()))
                .thenReturn(List.of(COMPONENT_ENTITY_READ_1, COMPONENT_ENTITY_READ_2, COMPONENT_ENTITY_READ_3));

        Assertions.assertThat(componentServiceImpl.saveAll(List.of(CREATE_COMPONENT_DTO_1, CREATE_COMPONENT_DTO_2, CREATE_COMPONENT_DTO_3)))
                .contains(1l, 2l, 3l);

        verify(componentRepository, times(1))
                .saveAll(anyList());
    }
}
