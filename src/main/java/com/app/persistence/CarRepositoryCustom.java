package com.app.persistence;

import com.app.persistence.view.GroupByView;
import com.app.persistence.view.GroupByAndPriceStatisticView;

import java.util.List;

/**
 * <p>
 * This interface provides methods for custom queries that allow grouping of car entities
 * based on specified fields and retrieving statistics related to the price and other fields.
 * </p>
 */
public interface CarRepositoryCustom {

    /**
     * Groups the car entities by a specific field and returns the counts for each group.
     *
     * @param field the name of the field to group by.
     * @return a list of {@link GroupByView} objects containing the grouped field values and their counts.
     */
    List<GroupByView<Object>> groupByField(String field);

    /**
     * Groups the car entities by a specific field and returns the price statistics (min, max) for each group.
     *
     * @param field the name of the field to group by.
     * @return a list of {@link GroupByAndPriceStatisticView} objects containing the grouped field values,
     *         as well as the minimum and maximum price for each group.
     */
    List<GroupByAndPriceStatisticView<Object>> groupByAndPriceStatisticField(String field);
}
