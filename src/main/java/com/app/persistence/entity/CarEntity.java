package com.app.persistence.entity;

import com.app.color.Color;
import com.app.controller.dto.car.CarDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a car entity in the database.
 * <p>
 * The {@code CarEntity} class is a JPA entity representing a car record in the database.
 * It contains the basic properties of a car, such as brand, model, price, speed, and color,
 * as well as the components associated with the car.
 * </p>
 * <p>
 * The entity uses a many-to-many relationship with the {@link ComponentEntity} class,
 * and it supports cascading operations for persisting and merging associated components.
 * </p>
 * <p>
 * The class is mapped to the {@code cars} table, with a unique constraint on the combination of
 * the {@code brand} and {@code model} columns to ensure uniqueness of each car model.
 * </p>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
@Table(
        name = "cars",
        uniqueConstraints = @UniqueConstraint(columnNames = {"brand", "model"}))
@Entity
public class CarEntity extends BaseEntity {

    /**
     * The brand of the car.
     */
    private String brand;

    /**
     * The model of the car.
     */
    private String model;

    /**
     * The price of the car.
     */
    private BigDecimal price;

    /**
     * The speed of the car (in km/h).
     */
    private int speed;

    /**
     * The color of the car.
     * The color is an enum, which is persisted as a string.
     */
    @Enumerated(EnumType.STRING)
    private Color color;

    /**
     * The components associated with the car.
     * This represents a many-to-many relationship between cars and components.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "cars_components",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "component_id")
    )
    @Builder.Default
    private List<ComponentEntity> components = new ArrayList<>();

    /**
     * Creates a new instance of {@link CarEntity} with the specified list of components.
     * <p>
     * This method creates a copy of the current car entity with a new list of components.
     * </p>
     *
     * @param components the list of components to associate with the car
     * @return a new {@code CarEntity} instance with the specified components
     */
    public CarEntity withComponents(List<ComponentEntity> components) {
        return CarEntity.builder()
                .brand(brand)
                .model(model)
                .price(price)
                .speed(speed)
                .color(color)
                .components(components)
                .build();
    }

    /**
     * Converts this {@link CarEntity} into a {@link CarDto} (Data Transfer Object).
     * <p>
     * This method maps the properties of the {@code CarEntity} to a {@code CarDto},
     * which is used for sending car data as a response in the API.
     * </p>
     *
     * @return a {@code CarDto} object representing this car entity
     */
    public CarDto toCarDto() {
        return new CarDto(id, brand, model, speed, price, color,
                components.stream().map(ComponentEntity::getName).toList());
    }
}
