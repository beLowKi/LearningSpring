package com.malcolm.learningspring.repositories;

import com.malcolm.learningspring.entities.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CategoryRepository extends CrudRepository<Category, UUID> {
}
