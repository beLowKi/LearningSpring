package com.malcolm.learningspring.specifications;

import com.malcolm.learningspring.entities.Order;
import com.malcolm.learningspring.enums.OrderStatus;
import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Utility class of methods returning Order Specifications.
 */
public final class OrderSpecifications {

    private OrderSpecifications() {
        throw new IllegalStateException("Utility class");
    }

    private static List<Predicate> getStatusFilters(
            Root<Order> root,
            CriteriaBuilder cb,
            Set<OrderStatus> statuses
    ) {

        List<Predicate> predicates = new ArrayList<>();

        // Adds OR filter for all statuses
        if (statuses != null && !statuses.isEmpty()) {
            List<Predicate> statusPredicates = new ArrayList<>();

            for (OrderStatus status : statuses) {
                statusPredicates.add(cb.equal(root.get("status"), status));
            }

            predicates.add(cb.or(statusPredicates.toArray(new Predicate[0])));
        }

        return predicates;
    }


    /**
     * Creates Specification that filters for Orders of a particular status.
     *
     * @param statuses Target OrderStatuses
     * @return Query specification
     */
    public static Specification<Order> byStatus(Set<OrderStatus> statuses) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Adds OR filter for all statuses
            if (statuses != null && !statuses.isEmpty()) {
                predicates.addAll(getStatusFilters(root, cb, statuses));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Creates Specification that filters for Orders of a particular status. Optionally,
     * can filter for Orders from a certain Customer as well.
     *
     * @param statuses Target OrderStatuses
     * @param customerId Optional target Customer ID
     * @return Query specification
     */
    public static Specification<Order> byStatus(
            Set<OrderStatus> statuses,
            UUID customerId
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Adds OR filter for all statuses
            if (statuses != null && !statuses.isEmpty()) {
                predicates.addAll(getStatusFilters(root, cb, statuses));
            }

            // Customer ID filter
            if (customerId != null) {
                predicates.add(cb.equal(root.get("customerId"), customerId));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
