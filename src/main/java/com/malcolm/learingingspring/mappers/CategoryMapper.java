package com.malcolm.learingingspring.mappers;

import com.malcolm.learingingspring.dtos.CategoryDto;
import com.malcolm.learingingspring.entities.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);
}
