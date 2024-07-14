package com.app.repository;

import com.app.car.Car;

import java.util.List;

/**
 * The interface Repository.
 *
 * @param <T> the type parameter
 */
public interface Repository<T> {
    /**
     * Gets all.
     *
     * @return the all
     */
    List<T> getAll();
}
