package com.malcolm.learingingspring.specifications;

import com.malcolm.learingingspring.entities.Category;
import com.malcolm.learingingspring.entities.Product;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Class of static methods that give query-filtering Product Specifications.
 */
public class ProductSpecifications {

    /**
     * Creates Specification that filters Product returns by category
     * and a price range--both of which are optional.
     *
     * @param categoryIds Set of category ids to include.
     * @param minPrice Minimum price
     * @param maxPrice Maximum price
     * @return Product Specification
     */
    public static Specification<Product> withFilters(
            Set<UUID> categoryIds,
            Double minPrice,
            Double maxPrice
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Adds category filter
            if ( categoryIds != null && !categoryIds.isEmpty() ) {
                Join<Product, Category> join = root.join("category", JoinType.INNER);
                predicates.add(join.get("id").in(categoryIds));
                query.distinct(true);
            }

            // Price filters
            if  ( minPrice != null ) {
                predicates.add(cb.ge(root.get("price"), minPrice));
            }
            if ( maxPrice != null ) {
                predicates.add(cb.le(root.get("price"), maxPrice));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
