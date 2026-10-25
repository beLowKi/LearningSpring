package com.malcolm.learingingspring.utils.models;

import java.io.Serializable;
import java.util.UUID;

public record CartItemId(
        UUID customerId,
        UUID catalogueItemId
) implements Serializable {}
