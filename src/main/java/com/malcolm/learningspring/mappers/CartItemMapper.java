package com.malcolm.learningspring.mappers;

import com.malcolm.learningspring.dtos.CartItemDto;
import com.malcolm.learningspring.entities.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItemDto toDto(CartItem cartItem);
    CartItem fromDto(CartItemDto cartItemDto);
}
