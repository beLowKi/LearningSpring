package com.malcolm.learningspring.mappers;

import com.malcolm.learningspring.dtos.OrderItemDto;
import com.malcolm.learningspring.entities.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemDto toDto(OrderItem item);
    OrderItem fromDto(OrderItem item);
}
