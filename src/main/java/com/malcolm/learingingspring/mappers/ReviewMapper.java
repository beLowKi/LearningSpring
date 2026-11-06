package com.malcolm.learingingspring.mappers;

import com.malcolm.learingingspring.dtos.ReviewDto;
import com.malcolm.learingingspring.entities.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewDto toDto(Review review);
    Review fromDto(ReviewDto review);
}
