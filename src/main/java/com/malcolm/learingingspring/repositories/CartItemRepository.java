package com.malcolm.learingingspring.repositories;

import com.malcolm.learingingspring.entities.CartItem;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CartItemRepository extends CrudRepository<CartItem, UUID> {
}
