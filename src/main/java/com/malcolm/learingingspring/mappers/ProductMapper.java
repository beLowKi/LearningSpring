package com.malcolm.learingingspring.mappers;

import com.malcolm.learingingspring.dtos.ProductDto;
import com.malcolm.learingingspring.entities.Product;
import org.hibernate.Hibernate;
import org.mapstruct.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, ReviewMapper.class})
public interface ProductMapper {

    ProductDto toDto(Product product, @Context Set<String> includes);
    Product fromDto(ProductDto productDto);

    @Condition
    default boolean isIncluded(
            @TargetPropertyName String property,
            @Context Set<String> includes,
            Collection<?> source
    ) {
        return includes.contains(property) && Hibernate.isInitialized(source);
    }

}
