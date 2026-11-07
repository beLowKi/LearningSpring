package com.malcolm.learningspring.dtos;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
public class CustomerDto {
    private UUID id;
    private String name;
    private String email;
    private String address;
    private Set<CartItemDto> cartItems;
    private Set<OrderDto> orders;
    private Set<ReviewDto> reviews;
}
