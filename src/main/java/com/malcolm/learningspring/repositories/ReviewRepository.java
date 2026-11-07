package com.malcolm.learningspring.repositories;

import com.malcolm.learningspring.entities.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ReviewRepository extends CrudRepository<Review, UUID> {
}
