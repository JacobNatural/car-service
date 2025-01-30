package com.app.persistence.impl;

import com.app.color.Color;
import com.app.persistence.CarSpecification;
import com.app.persistence.entity.CarEntity;
import com.app.persistence.entity.ComponentEntity;
import com.app.persistence.view.CarCriterionFilteringView;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarSpecificationImplTest {

    @Mock
    Root<CarEntity> root;

    @Mock
    CriteriaBuilder cb;

    @Mock
    Join<CarEntity, ComponentEntity> join;

    @Mock
    Predicate initialPredicate;

    @Mock
    Predicate likePredicate;

    CarSpecification carSpecification;


    @BeforeEach
    public void setUp() {
        carSpecification = new CarSpecificationImpl();
    }

    @Test
    @DisplayName("When filtering by brand, verify that the correct method (cb.like) is invoked")
    public void test1() {

        var filteringView = new CarCriterionFilteringView(
                "BMW", null,
                null, null,
                null, null,
                null, null);

        when(cb.conjunction()).thenReturn(initialPredicate);
        when(cb.like(root.get("brand"), "BMW")).thenReturn(likePredicate);
        when(cb.and(initialPredicate, likePredicate)).thenReturn(likePredicate);

        Specification<CarEntity> specification = carSpecification.dynamicFilters(filteringView);
        Predicate resultPredicate = specification.toPredicate(root, null, cb);

        assertNotNull(resultPredicate);

        Mockito.verify(cb, times(1))
                .like(root.get("brand"), "BMW");
        Mockito.verify(cb, times(1))
                .and(initialPredicate, likePredicate);
    }

    @Test
    @DisplayName("When filtering by model, verify that the correct method (cb.like) is invoked")
    public void test2() {

        var filteringView = new CarCriterionFilteringView(
                null, "A3",
                null, null,
                null, null,
                null, null);

        when(cb.conjunction()).thenReturn(initialPredicate);
        when(cb.like(root.get("model"), "A3")).thenReturn(likePredicate);
        when(cb.and(initialPredicate, likePredicate)).thenReturn(likePredicate);

        Specification<CarEntity> specification = carSpecification.dynamicFilters(filteringView);
        Predicate resultPredicate = specification.toPredicate(root, null, cb);

        assertNotNull(resultPredicate);

        Mockito.verify(cb, times(1))
                .like(root.get("model"), "A3");
        Mockito.verify(cb, times(1))
                .and(initialPredicate, likePredicate);
    }

    @Test
    @DisplayName("When filtering by min speed, verify that the correct method (cb.greaterThanOrEqualTo) is invoked")
    public void test3() {

        var filteringView = new CarCriterionFilteringView(
                "", null,
                150, null,
                null, null,
                null, null);

        when(cb.conjunction()).thenReturn(initialPredicate);
        when(cb.greaterThanOrEqualTo(root.get("minSpeed"), 150)).thenReturn(likePredicate);
        when(cb.and(initialPredicate, likePredicate)).thenReturn(likePredicate);

        Specification<CarEntity> specification = carSpecification.dynamicFilters(filteringView);
        Predicate resultPredicate = specification.toPredicate(root, null, cb);

        assertNotNull(resultPredicate);

        Mockito.verify(cb, times(1))
                .greaterThanOrEqualTo(root.get("minSpeed"), 150);
        Mockito.verify(cb, times(1))
                .and(initialPredicate, likePredicate);
    }

    @Test
    @DisplayName("When filtering by max speed, verify that the correct method (cb.lessThanOrEqualTo) is invoked")
    public void test4() {

        var filteringView = new CarCriterionFilteringView(
                null, "",
                null, 200,
                null, null,
                null, List.of());

        when(cb.conjunction()).thenReturn(initialPredicate);
        when(cb.lessThanOrEqualTo(root.get("maxSpeed"), 200)).thenReturn(likePredicate);
        when(cb.and(initialPredicate, likePredicate)).thenReturn(likePredicate);

        Specification<CarEntity> specification = carSpecification.dynamicFilters(filteringView);
        Predicate resultPredicate = specification.toPredicate(root, null, cb);

        assertNotNull(resultPredicate);

        Mockito.verify(cb, times(1))
                .lessThanOrEqualTo(root.get("maxSpeed"), 200);
        Mockito.verify(cb, times(1))
                .and(initialPredicate, likePredicate);
    }

    @Test
    @DisplayName("When filtering by min price, verify that the correct method (cb.greaterThanOrEqualTo) is invoked")
    public void test5() {

        var filteringView = new CarCriterionFilteringView(
                null, null,
                null, null,
                BigDecimal.ONE, null,
                null, null);

        when(cb.conjunction()).thenReturn(initialPredicate);
        when(cb.greaterThanOrEqualTo(root.get("minPrice"), BigDecimal.ONE)).thenReturn(likePredicate);
        when(cb.and(initialPredicate, likePredicate)).thenReturn(likePredicate);

        Specification<CarEntity> specification = carSpecification.dynamicFilters(filteringView);
        Predicate resultPredicate = specification.toPredicate(root, null, cb);

        assertNotNull(resultPredicate);

        Mockito.verify(cb, times(1))
                .greaterThanOrEqualTo(root.get("minPrice"), BigDecimal.ONE);
        Mockito.verify(cb, times(1))
                .and(initialPredicate, likePredicate);
    }

    @Test
    @DisplayName("When filtering by max price, verify that the correct method (cb.lessThanOrEqualTo) is invoked")
    public void test6() {

        var filteringView = new CarCriterionFilteringView(
                null, null,
                null, null,
                null, BigDecimal.ONE,
                null, null);

        when(cb.conjunction()).thenReturn(initialPredicate);
        when(cb.lessThanOrEqualTo(root.get("maxPrice"), BigDecimal.ONE)).thenReturn(likePredicate);
        when(cb.and(initialPredicate, likePredicate)).thenReturn(likePredicate);

        Specification<CarEntity> specification = carSpecification.dynamicFilters(filteringView);
        Predicate resultPredicate = specification.toPredicate(root, null, cb);

        assertNotNull(resultPredicate);

        Mockito.verify(cb, times(1))
                .lessThanOrEqualTo(root.get("maxPrice"), BigDecimal.ONE);
        Mockito.verify(cb, times(1))
                .and(initialPredicate, likePredicate);
    }

    @Test
    @DisplayName("When filtering by color, verify that the correct method (cb.equal) is invoked")
    public void test7() {


        var filteringView = new CarCriterionFilteringView(
                null, null,
                null, null,
                null, null,
                Color.BLACK, null);
        when(cb.conjunction()).thenReturn(initialPredicate);
        when(cb.equal(root.get("color"), Color.BLACK)).thenReturn(likePredicate);
        when(cb.and(initialPredicate, likePredicate)).thenReturn(likePredicate);

        Specification<CarEntity> specification = carSpecification.dynamicFilters(filteringView);
        Predicate resultPredicate = specification.toPredicate(root, null, cb);
        assertNotNull(resultPredicate);

        Mockito.verify(cb, times(1))
                .equal(root.get("color"), Color.BLACK);
        Mockito.verify(cb, times(1))
                .and(initialPredicate, likePredicate);
    }
}
