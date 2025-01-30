package com.app.persistence;

import com.app.persistence.entity.CarEntity;
import com.app.persistence.view.CarCriterionFilteringView;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * Interface for dynamic filtering of car entities based on specified criteria.
 * <p>
 * This interface provides a method for creating a {@link Specification} that can be used to filter car entities
 * based on various fields such as brand, model, price, speed, and components.
 * </p>
 */
@Component
public interface CarSpecification {

    /**
     * Creates a {@link Specification} for filtering car entities based on the given criteria.
     * <p>
     * This method allows dynamic filtering based on multiple criteria, such as brand, model, color,
     * price range, speed range, and components. The filters are applied only if the respective values
     * are provided in the input object.
     * </p>
     *
     * @param criterionFilteringView the filtering criteria containing the values to filter by.
     * @return a {@link Specification} that can be used to filter car entities.
     */
    Specification<CarEntity> dynamicFilters(CarCriterionFilteringView criterionFilteringView);
}
