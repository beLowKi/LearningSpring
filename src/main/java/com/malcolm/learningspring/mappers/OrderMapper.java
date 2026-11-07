package com.malcolm.learningspring.mappers;

import com.malcolm.learningspring.dtos.OrderDto;
import com.malcolm.learningspring.entities.Order;
import org.hibernate.Hibernate;
import org.mapstruct.Condition;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.TargetPropertyName;

import java.util.Collection;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {
    OrderDto toDto(Order order, @Context Set<String> includes);
    Order fromDto(OrderDto orderDto);
    
    @Condition
    default boolean isIncluded(
            @TargetPropertyName String property,
            @Context Set<String> includes,
            Collection<?> source
    ) {
        return includes.contains(property) && Hibernate.isInitialized(source);
    }
}
