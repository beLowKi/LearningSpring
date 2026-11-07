package com.malcolm.learningspring.repositories;

import com.malcolm.learningspring.entities.OrderItem;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrderItemRepository extends CrudRepository<OrderItem, UUID> {
    
}
