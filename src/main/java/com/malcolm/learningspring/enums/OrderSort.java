package com.malcolm.learningspring.enums;

import lombok.Getter;

public enum OrderSort {
    ID("id"),
    SHIPPING_ADDRESS("shipping_address"),
    CREATED_AT("created_at");

    @Getter
    private final String value;

    OrderSort(String value) {
        this.value = value;
    }
}
