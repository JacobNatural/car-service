package com.app.controller;

import com.app.controller.dto.ResponseDto;
import com.app.persistence.ComponentRepository;
import com.app.persistence.entity.ComponentEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
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

import java.util.List;

import static com.app.data.CarComponentData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ComponentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ComponentRepository componentRepository;

    private ComponentEntity component_entity_save_1;
    private ComponentEntity component_entity_save_2;


        @BeforeEach
        public void setup() {
            component_entity_save_1 = ComponentEntity.builder().name("AIR CONDITION").build();
            component_entity_save_2 = ComponentEntity.builder().name("SUNROOF").build();

        }


    @Test
    @DisplayName("When creating a component, return the correct status and ID related to the created component.")
    @SneakyThrows
    public void test1() {

        mockMvc.perform(post("/components")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CREATE_COMPONENT_DTO_1)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.data").value(1L));
    }

    @Test
    @DisplayName("When creating a component, if a component with this name exists, return a conflict status and a message.")
    @SneakyThrows
    public void test2() {

        componentRepository.save(component_entity_save_1);

        mockMvc.perform(post("/components")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CREATE_COMPONENT_DTO_1)))
                .andExpect(status().isConflict())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.error")
                        .value("An entry with the given key already exists in the database."));
    }

    @Test
    @DisplayName("When creating a components, return the correct status and IDs related to the created components.")
    @SneakyThrows
    public void test3() {

        mockMvc.perform(post("/components/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(CREATE_COMPONENT_DTO_1, CREATE_COMPONENT_DTO_2))))
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.data").value(Matchers.containsInAnyOrder(1, 2)));
    }

    @Test
    @DisplayName("When finding a component by ID, return the component related to the ID.")
    @SneakyThrows
    public void test4() {

        componentRepository.save(component_entity_save_1);

        mockMvc.perform(get("/components/1"))
                .andExpect(status().isFound())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(content().json(objectMapper
                        .writeValueAsString(new ResponseDto<>(COMPONENT_DTO_1, null))));
    }

    @Test
    @DisplayName("When finding a components by IDs, return the found status and components related to the IDs.")
    @SneakyThrows
    public void test5() {

        componentRepository.saveAll(List.of(component_entity_save_1, component_entity_save_2));

        mockMvc.perform(get("/components")
                        .queryParam("ids", "1", "2"))
                .andExpect(status().isFound())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(content().json(objectMapper
                        .writeValueAsString(new ResponseDto<>(List.of(COMPONENT_DTO_1, COMPONENT_DTO_2), null))));
    }

    @Test
    @DisplayName("When delete a component by ID, return the ok status and ID related to the deleted component.")
    @SneakyThrows
    public void test6() {

        componentRepository.save(component_entity_save_1);

        mockMvc.perform(delete("/components/1"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.data").value(1));
    }

    @Test
    @DisplayName("When deleting a component by ID, if the component with the related ID does not exist, " +
            "return a not found status and an error message.")
    @SneakyThrows
    public void test7() {

        mockMvc.perform(delete("/components/1"))
                .andExpect(status().isNotFound())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.error").value("Element not found."));
    }

    @Test
    @DisplayName("When delete a components by IDs, return the ok status and IDs related to the deleted component.")
    @SneakyThrows
    public void test8() {

        componentRepository.saveAll(List.of(component_entity_save_1, component_entity_save_2));

        mockMvc.perform(delete("/components")
                        .queryParam("ids", "1", "2"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.data").value(Matchers.containsInAnyOrder(1, 2)));
    }
}
