package com.malcolm.learningspring.repositories;

import com.malcolm.learningspring.entities.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends CrudRepository<Order, UUID>, JpaSpecificationExecutor<Order> {
    @Query(value = "SELECT * FROM orders WHERE customer_id = ?1", nativeQuery = true)
    List<Order> findAllByCustomerId(UUID customerId, Sort sort);
}
