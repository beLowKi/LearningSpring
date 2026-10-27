package com.malcolm.learingingspring.mappers;

import com.malcolm.learingingspring.dtos.OrderDto;
import com.malcolm.learingingspring.entities.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
}
