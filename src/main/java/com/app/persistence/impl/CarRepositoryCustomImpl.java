package com.app.persistence.impl;

import com.app.persistence.CarRepositoryCustom;
import com.app.persistence.entity.CarEntity;
import com.app.persistence.view.GroupByView;
import com.app.persistence.view.GroupByAndPriceStatisticView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Custom implementation of the {@link CarRepositoryCustom} interface.
 * <p>
 * This class provides custom queries for the {@link CarEntity} class using the JPA Criteria API.
 * It includes methods for grouping cars by a given field and for retrieving price statistics.
 * </p>
 */
@Repository
public class CarRepositoryCustomImpl implements CarRepositoryCustom {

    /**
     * The {@link EntityManager} instance used to interact with the database.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Groups cars by a specified field and counts the number of cars in each group.
     * <p>
     * This method dynamically groups the cars by a specified field (e.g., brand, model, color)
     * and counts the number of cars in each group. The result is returned as a list of {@link GroupByView} objects.
     * </p>
     *
     * @param field the field to group by (e.g., "brand", "model", "color")
     * @return a list of {@link GroupByView} objects representing the grouped cars
     */
    @Override
    public List<GroupByView<Object>> groupByField(String field) {
        var cb = em.getCriteriaBuilder();
        var query = cb.createQuery(Object[].class);
        var root = query.from(CarEntity.class);

        query.groupBy(root.get(field));
        query.multiselect(root.get(field), cb.count(root));

        var result = em.createQuery(query).getResultList();
        return result
                .stream()
                .map(el -> new GroupByView<>(el[0], (Long) el[1]))
                .toList();
    }

    /**
     * Groups cars by a specified field and retrieves price statistics (minimum and maximum price) for each group.
     * <p>
     * This method dynamically groups the cars by a specified field (e.g., brand, model, color)
     * and calculates the minimum and maximum price for each group. The result is returned as a list of
     * {@link GroupByAndPriceStatisticView} objects.
     * </p>
     *
     * @param field the field to group by (e.g., "brand", "model", "color")
     * @return a list of {@link GroupByAndPriceStatisticView} objects representing the grouped cars with price statistics
     */
    public List<GroupByAndPriceStatisticView<Object>> groupByAndPriceStatisticField(String field) {
        var cb = em.getCriteriaBuilder();
        var query = cb.createQuery(Object[].class);
        var root = query.from(CarEntity.class);

        query.groupBy(root.get(field));
        query.multiselect(root.get(field), cb.min(root.get("price")), cb.max(root.get("price")));

        var result = em.createQuery(query).getResultList();

        return result
                .stream()
                .map(element ->
                        new GroupByAndPriceStatisticView<>(element[0], (BigDecimal) element[1], (BigDecimal) element[2]))
                .toList();
    }
}
