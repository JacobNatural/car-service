package com.app.service;

import java.util.List;

/**
 * The {@code CrudService} interface provides the basic CRUD (Create, Read, Update, Delete) operations
 * for any entity type {@code T} with the identifier type {@code U}.
 * <p>
 * This interface defines standard methods for interacting with entities, such as finding, deleting,
 * and retrieving lists of entities by their IDs.
 * </p>
 *
 */
public interface CrudService<T, U> {

    /**
     * Finds an entity by its unique identifier.
     *
     * @param id the unique identifier of the entity to be found
     * @return the entity with the given identifier
     * @throws jakarta.persistence.EntityNotFoundException if the entity with the given ID is not found
     */
    T findById(U id);

    /**
     * Finds a list of entities by their unique identifiers.
     *
     * @param ids the list of identifiers of the entities to be found
     * @return a list of entities with the given identifiers
     * @throws jakarta.persistence.EntityNotFoundException if some of the entities with the given IDs are not found
     */
    List<T> findAllById(List<U> ids);

    /**
     * Retrieves all entities of type {@code T}.
     *
     * @return a list of all entities
     */
    List<T> findAll();

    /**
     * Deletes an entity by its unique identifier.
     *
     * @param id the unique identifier of the entity to be deleted
     * @return the ID of the deleted entity
     * @throws jakarta.persistence.EntityNotFoundException if the entity with the given ID is not found
     */
    U deleteById(U id);

    /**
     * Deletes a list of entities by their unique identifiers.
     *
     * @param ids the list of identifiers of the entities to be deleted
     * @return a list of the IDs of the deleted entities
     * @throws jakarta.persistence.EntityNotFoundException if some of the entities with the given IDs are not found
     */
    List<U> deleteAllById(List<U> ids);
}
