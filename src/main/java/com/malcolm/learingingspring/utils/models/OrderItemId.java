package com.malcolm.learingingspring.utils.models;

import java.io.Serializable;
import java.util.UUID;

public record OrderItemId(
        UUID orderId,
        UUID catalogueItemId
) implements Serializable {}
