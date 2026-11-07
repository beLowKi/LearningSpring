package com.malcolm.learningspring.mappers;

import com.malcolm.learningspring.dtos.ReviewDto;
import com.malcolm.learningspring.entities.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewDto toDto(Review review);
    Review fromDto(ReviewDto review);
}
