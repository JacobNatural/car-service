package com.app.persistence;

import com.app.persistence.entity.ComponentEntity;
import com.app.persistence.view.ComponentsAndCarsView;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository interface for managing {@link ComponentEntity} entities.
 * <p>
 * This interface extends {@link CrudRepository} and provides additional methods for querying components
 * and their associated cars. It includes a custom query method for retrieving a list of components
 * along with information about the cars that have those components.
 * </p>
 */
public interface ComponentRepository extends CrudRepository<ComponentEntity, Long> {

    /**
     * Retrieves a list of components and the cars that are associated with them.
     * <p>
     * This method performs a join between the components and their associated cars and retrieves the
     * component name and car details (such as car ID, brand, model, color, price, and speed).
     * The result is grouped by the component and car details.
     * </p>
     *
     * @return a list of {@link ComponentsAndCarsView} objects containing component and car information.
     */
    @Query("""
            select new com.app.persistence.view.ComponentsAndCarsView( 
                            c.name, car.id, car.brand, car.model, car.color, car.price, car.speed)
                    from ComponentEntity c
                    join c.cars car
                    group by c.name, car.id, car.brand, car.model, car.color, car.price, car.speed
            """)
    List<ComponentsAndCarsView> groupByComponent();
}
