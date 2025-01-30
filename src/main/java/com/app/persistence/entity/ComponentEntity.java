package com.app.persistence.entity;

import com.app.controller.dto.components.ComponentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a component entity in the database.
 * <p>
 * The {@code ComponentEntity} class is a JPA entity representing a car component record in the database.
 * Each component can be associated with multiple cars, and this relationship is represented by a
 * many-to-many relationship with the {@link CarEntity} class.
 * </p>
 * <p>
 * The entity is mapped to the {@code components} table, and the name of each component must be unique
 * within the database.
 * </p>
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "components")
public class ComponentEntity extends BaseEntity {

    /**
     * The name of the component.
     * This field must be unique and cannot be null.
     */
    @Column(unique = true, nullable = false)
    private String name;

    /**
     * The cars associated with this component.
     * This represents a many-to-many relationship between components and cars.
     */
    @ManyToMany
    @JoinTable(
            name = "cars_components",
            joinColumns = @JoinColumn(name = "component_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id")
    )
    @Builder.Default
    private List<CarEntity> cars = new ArrayList<>();

    /**
     * Converts this {@link ComponentEntity} into a {@link ComponentDto} (Data Transfer Object).
     * <p>
     * This method maps the properties of the {@code ComponentEntity} to a {@code ComponentDto},
     * which is used for sending component data as a response in the API.
     * </p>
     *
     * @return a {@code ComponentDto} object representing this component entity
     */
    public ComponentDto toComponentDto() {
        return new ComponentDto(id, name);
    }
}
