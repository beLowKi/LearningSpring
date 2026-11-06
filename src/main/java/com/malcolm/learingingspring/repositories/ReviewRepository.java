package com.malcolm.learingingspring.repositories;

import com.malcolm.learingingspring.entities.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ReviewRepository extends CrudRepository<Review, UUID> {
}
