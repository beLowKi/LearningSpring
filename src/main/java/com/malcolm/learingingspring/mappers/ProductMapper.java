package com.malcolm.learingingspring.mappers;

import com.malcolm.learingingspring.dtos.ProductDto;
import com.malcolm.learingingspring.entities.Customer;
import com.malcolm.learingingspring.entities.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);
}
