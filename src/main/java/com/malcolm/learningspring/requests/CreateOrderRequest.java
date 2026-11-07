package com.malcolm.learningspring.requests;

import com.malcolm.learningspring.dtos.CartItemDto;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class CreateOrderRequest {
    UUID customerId;
    Set<CartItemDto> items;
    String address;
}
