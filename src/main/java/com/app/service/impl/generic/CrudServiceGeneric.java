package com.app.service.impl.generic;

import com.app.persistence.CrudRepository;
import com.app.service.CrudService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * A generic service implementation for CRUD operations, providing common
 * functionality for managing entities.
 * <p>
 * This abstract class implements the {@link CrudService} interface and
 * provides default implementations for the basic CRUD operations, such as
 * finding, deleting, and listing entities by ID. The class is generic, which
 * means it can be used with any entity type and identifier type.
 * </p>
 *
 * @param <T> the type of the entity.
 * @param <U> the type of the identifier (primary key) of the entity.
 */
@RequiredArgsConstructor
public abstract class CrudServiceGeneric<T, U> implements CrudService<T, U> {

    /**
     * The repository used to perform CRUD operations on the entity.
     */
    protected final CrudRepository<T, U> crudRepository;

    /**
     * Finds an entity by its identifier.
     *
     * @param id the identifier of the entity to be found.
     * @return the entity found by the given identifier.
     * @throws EntityNotFoundException if no entity is found with the given identifier.
     */
    public T findById(U id) {
        return crudRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Element not found."));
    }

    /**
     * Finds all entities by their identifiers.
     *
     * @param ids a list of identifiers of the entities to be found.
     * @return a list of entities found by the given identifiers.
     * @throws EntityNotFoundException if not all entities are found for the given identifiers.
     */
    public List<T> findAllById(List<U> ids) {
        var elements = crudRepository.findAllById(ids);

        if (elements.size() != ids.size()) {
            throw new EntityNotFoundException("Not all elements were found.");
        }

        return elements;
    }

    /**
     * Finds all entities.
     *
     * @return a list of all entities.
     */
    public List<T> findAll() {
        return crudRepository.findAll();
    }

    /**
     * Deletes an entity by its identifier.
     *
     * @param id the identifier of the entity to be deleted.
     * @return the identifier of the deleted entity.
     * @throws EntityNotFoundException if no entity is found with the given identifier.
     */
    public U deleteById(U id) {
        var element = crudRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Element not found."));
        crudRepository.delete(element);
        return id;
    }

    /**
     * Deletes all entities by their identifiers.
     *
     * @param ids a list of identifiers of the entities to be deleted.
     * @return a list of identifiers of the deleted entities.
     * @throws EntityNotFoundException if not all entities are found for the given identifiers.
     */
    public List<U> deleteAllById(List<U> ids) {
        var elements = crudRepository.findAllById(ids);
        if (elements.size() != ids.size()) {
            throw new EntityNotFoundException("Not all elements were found.");
        }

        crudRepository.deleteAll(elements);

        return ids;
    }
}
