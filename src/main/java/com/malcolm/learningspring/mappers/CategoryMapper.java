package com.malcolm.learningspring.mappers;

import com.malcolm.learningspring.dtos.CategoryDto;
import com.malcolm.learningspring.entities.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);
}
