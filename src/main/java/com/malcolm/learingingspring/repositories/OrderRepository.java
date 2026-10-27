package com.malcolm.learingingspring.repositories;

import com.malcolm.learingingspring.entities.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrderRepository extends CrudRepository<Order, UUID> {
}
