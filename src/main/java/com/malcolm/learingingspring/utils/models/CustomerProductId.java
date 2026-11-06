package com.malcolm.learingingspring.utils.models;

import java.io.Serializable;
import java.util.UUID;

public record CustomerProductId(
        UUID customerId,
        UUID productId
) implements Serializable {}
