package com.app.controller;

import com.app.controller.dto.components.ComponentDto;
import com.app.controller.dto.components.CreateComponentDto;
import com.app.controller.dto.ResponseDto;
import com.app.persistence.entity.ComponentEntity;
import com.app.service.ComponentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing car components.
 * Provides endpoints for creating, updating, retrieving, and deleting car components.
 */
@RestController
@RequestMapping("/components")
@RequiredArgsConstructor
public class ComponentsController {

    private final ComponentService componentService;

    /**
     * Adds a new car component.
     *
     * @param createComponentDto Data required to create a component.
     * @return ResponseDto containing the ID of the created component.
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto<Long> addComponent(@RequestBody CreateComponentDto createComponentDto) {
        return new ResponseDto<>(componentService.save(createComponentDto));
    }

    /**
     * Adds multiple new car components.
     *
     * @param createComponents List of data required to create multiple components.
     * @return ResponseDto containing a list of IDs of the created components.
     */
    @PostMapping("/all")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto<List<Long>> addComponents(@RequestBody List<CreateComponentDto> createComponents) {
        return new ResponseDto<>(componentService.saveAll(createComponents));
    }

    /**
     * Retrieves a car component by its ID.
     *
     * @param id The ID of the component to retrieve.
     * @return ResponseDto containing the component data.
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseDto<ComponentDto> findComponentById(@PathVariable Long id) {
        return new ResponseDto<>(componentService.findById(id).toComponentDto());
    }

    /**
     * Retrieves a list of car components by their IDs.
     *
     * @param ids List of component IDs.
     * @return ResponseDto containing a list of component data.
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseDto<List<ComponentDto>> findComponents(@RequestParam List<Long> ids) {
        return new ResponseDto<>(componentService
                .findAllById(ids)
                .stream()
                .map(ComponentEntity::toComponentDto)
                .toList());
    }

    /**
     * Deletes a car component by its ID.
     *
     * @param id The ID of the component to delete.
     * @return ResponseDto containing the ID of the deleted component.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<Long> deleteComponentById(@PathVariable Long id) {
        return new ResponseDto<>(componentService.deleteById(id));
    }

    /**
     * Deletes multiple car components by their IDs.
     *
     * @param ids List of component IDs to delete.
     * @return ResponseDto containing a list of deleted component IDs.
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<List<Long>> deleteComponents(@RequestParam List<Long> ids) {
        return new ResponseDto<>(componentService.deleteAllById(ids));
    }
}
