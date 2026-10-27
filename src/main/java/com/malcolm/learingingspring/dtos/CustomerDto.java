package com.malcolm.learingingspring.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class CustomerDto {
    private UUID id;
    private String name;
    private String email;
//    private Set<OrderDto> orders;
//    private Set<CartItemDto> cartItems;
}
