package com.app.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * A custom repository interface that extends {@link JpaRepository} and provides
 * the basic CRUD operations for managing entities.
 * <p>
 * This interface is intended to be extended by other repository interfaces to provide
 * common CRUD functionality (create, read, update, delete) without the need to repeat
 * code in each repository. It is annotated with {@link NoRepositoryBean} to ensure
 * that it is not instantiated directly.
 * </p>
 *
 * @param <T> the type of the entity to be managed by the repository.
 * @param <U> the type of the identifier (primary key) of the entity.
 */
@NoRepositoryBean
public interface CrudRepository<T, U> extends JpaRepository<T, U> {
}
