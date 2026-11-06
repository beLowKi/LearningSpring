package com.malcolm.learingingspring.repositories;

import com.malcolm.learingingspring.entities.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CategoryRepository extends CrudRepository<Category, UUID> {
}
