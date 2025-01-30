package com.app.persistence.impl;

import com.app.persistence.CarSpecification;
import com.app.persistence.entity.CarEntity;
import com.app.persistence.entity.ComponentEntity;
import com.app.persistence.view.CarCriterionFilteringView;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link CarSpecification} interface that provides dynamic filters for car entities.
 * <p>
 * This class creates dynamic {@link Specification} objects to filter cars based on various criteria such as brand,
 * model, color, price range, speed range, and associated components.
 * </p>
 */
@Component
public class CarSpecificationImpl implements CarSpecification {

    /**
     * Generates a {@link Specification} based on the provided filtering criteria.
     * <p>
     * This method constructs a {@link Specification} for filtering {@link CarEntity} objects based on the given
     * {@link CarCriterionFilteringView}. The filters include brand, model, color, price range, speed range, and associated
     * components.
     * </p>
     *
     * @param criterionFilteringView the filtering criteria to apply to the query
     * @return a {@link Specification} for filtering {@link CarEntity} objects
     */
    public Specification<CarEntity> dynamicFilters(CarCriterionFilteringView criterionFilteringView) {

        return ((root, query, cb) -> {
            // Create conjunction to hold all conditions
            var p = cb.conjunction();

            // Join components for filtering by component name
            Join<CarEntity, ComponentEntity> comp = root.join("components");

            // Add brand filter if provided
            if (criterionFilteringView.brand() != null && !criterionFilteringView.brand().isEmpty()) {
                p = cb.and(
                        p,
                        cb.like(root.get("brand"), criterionFilteringView.brand())
                );
            }

            // Add model filter if provided
            if (criterionFilteringView.model() != null && !criterionFilteringView.model().isEmpty()) {
                p = cb.and(
                        p,
                        cb.like(root.get("model"), criterionFilteringView.model())
                );
            }

            // Add color filter if provided
            if (criterionFilteringView.color() != null) {
                p = cb.and(
                        p,
                        cb.equal(root.get("color"), criterionFilteringView.color())
                );
            }

            // Add minimum price filter if provided
            if (criterionFilteringView.minPrice() != null) {
                p = cb.and(
                        p,
                        cb.greaterThanOrEqualTo(root.get("price"), criterionFilteringView.minPrice())
                );
            }

            // Add maximum price filter if provided
            if (criterionFilteringView.maxPrice() != null) {
                p = cb.and(
                        p,
                        cb.lessThanOrEqualTo(root.get("price"), criterionFilteringView.maxPrice())
                );
            }

            // Add minimum speed filter if provided
            if (criterionFilteringView.minSpeed() != null) {
                p = cb.and(
                        p,
                        cb.greaterThanOrEqualTo(root.get("speed"), criterionFilteringView.minSpeed())
                );
            }

            // Add maximum speed filter if provided
            if (criterionFilteringView.maxSpeed() != null) {
                p = cb.and(
                        p,
                        cb.lessThanOrEqualTo(root.get("speed"), criterionFilteringView.maxSpeed())
                );
            }

            // Add components filter if provided
            if (criterionFilteringView.components() != null && !criterionFilteringView.components().isEmpty()) {
                p = cb.and(
                        p,
                        comp.get("name").in(criterionFilteringView.components())
                );
            }

            return p;
        });
    }
}
