package com.malcolm.learingingspring.repositories;

import com.malcolm.learingingspring.entities.OrderItem;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrderItemRepository extends CrudRepository<OrderItem, UUID> {
    
}
