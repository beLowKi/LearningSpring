package com.malcolm.learningspring.utils.models;

import java.io.Serializable;
import java.util.UUID;

public record CustomerProductId(
        UUID customerId,
        UUID productId
) implements Serializable {}
