package com.malcolm.learingingspring.repositories;

import com.malcolm.learingingspring.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

//    @Query("""
//        SELECT p FROM Product p
//        WHERE (
//            SELECT COUNT(DISTINCT c)
//            FROM p.categories c
//            WHERE c IN :categoryIds
//        ) = :categoryIdsSize
//    """)

    @Query(
            value = """
                SELECT DISTINCT p.*
                FROM products p
                INNER JOIN product_categories pc ON p.id = pc.product_id
                WHERE pc.category_id IN :categoryIds;
            """,
            nativeQuery = true
    )
    List<Product> findAllIn(
            @Param("categoryIds") Set<UUID> categoryIds,
            @Param("minPrice") double minPrice,
            @Param("maxPrice") double maxPrice
    );
}
