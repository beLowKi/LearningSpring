package com.malcolm.learningspring.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.malcolm.learningspring.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
public class OrderDto {
    UUID id;
    UUID customerId;

    // FIXME this isn't mapping correctly
    @Enumerated(EnumType.STRING)
    OrderStatus status;

    String shippingAddressSnapshot;
    Timestamp createdAt;
    Set<OrderItemDto> items;
}
