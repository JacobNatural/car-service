package com.app.persistence;

import com.app.persistence.entity.CarEntity;
import com.app.persistence.view.PriceSpeedStatisticView;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link CarEntity}, extending the basic CRUD functionality and custom queries.
 * <p>
 * This interface provides methods for performing operations on the {@link CarEntity} data, including
 * filtering, aggregation, and complex queries related to car statistics.
 * </p>
 */
public interface CarRepository extends CrudRepository<CarEntity, Long>, CarRepositoryCustom {

    /**
     * Finds all cars with a speed between the given range.
     *
     * @param speedFrom the lower bound of the speed.
     * @param speedTo   the upper bound of the speed.
     * @return a list of {@link CarEntity} objects that match the speed range.
     */
    List<CarEntity> findCarEntitiesBySpeedBetween(int speedFrom, int speedTo);

    /**
     * Finds all cars based on the given specifications.
     *
     * @param spec the specification used for filtering the cars.
     * @return a list of {@link CarEntity} objects that match the specification.
     */
    List<CarEntity> findAll(Specification<CarEntity> spec);

    /**
     * Fetches statistics on car prices and speeds, including the minimum, maximum, and average values.
     *
     * @return an {@link Optional} containing a {@link PriceSpeedStatisticView} with the statistics of the cars.
     */
    @Query("""
            select new com.app.persistence.view.PriceSpeedStatisticView(
            min(ce.price), max(ce.price), avg(ce.price), min(ce.speed), max(ce.speed), avg(ce.speed)) 
            from CarEntity  ce
            """)
    Optional<PriceSpeedStatisticView> findPriceSpeedStatistics();

    /**
     * Finds the car that is closest in price to the provided price.
     *
     * @param price the price to compare the cars to.
     * @return a list of {@link CarEntity} objects with the price closest to the given price.
     */
    @Query("""
            select c from CarEntity c 
            where abs(c.price - :price) = (
                select min(abs(car.price - :price)) 
                from CarEntity car
            )
            """)
    List<CarEntity> findCarsCloseToPrice(@Param("price") BigDecimal price);
}
