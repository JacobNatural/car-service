package com.app.repository;

import java.util.List;

/**
 * A generic interface for repositories that manage collections of entities.
 * <p>
 * This interface defines a method for retrieving all entities of type {@code T} managed by the repository.
 * </p>
 *
 * @param <T> the type of entities managed by the repository
 */
public interface Repository<T> {

    /**
     * Retrieves all entities managed by the repository.
     *
     * @return a {@link List} containing all entities of type {@code T}
     */
    List<T> getAll();
}