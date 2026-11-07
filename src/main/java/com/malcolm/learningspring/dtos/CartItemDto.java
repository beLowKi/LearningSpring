package com.malcolm.learningspring.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class CartItemDto {
    UUID customerId;
    UUID productId;
    int quantity;
}
