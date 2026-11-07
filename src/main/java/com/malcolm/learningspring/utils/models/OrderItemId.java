package com.malcolm.learningspring.utils.models;

import java.io.Serializable;
import java.util.UUID;

public record OrderItemId(
        UUID orderId,
        UUID productId
) implements Serializable {}
