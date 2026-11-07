package com.malcolm.learningspring.repositories;

import com.malcolm.learningspring.entities.CartItem;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface CartItemRepository extends CrudRepository<CartItem, UUID> {
    @Query(value = "SELECT * FROM cart_items WHERE customer_id = ?1", nativeQuery = true)
    List<CartItem> findAllByCustomerId(UUID customerId, Sort sort);
}
