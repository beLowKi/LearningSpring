package com.malcolm.learningspring.enums;

import lombok.Getter;

public enum CartItemSort {
    CUSTOMER_ID("customerId"),
    PRODUCT_ID("productId"),
    QUANTITY("quantity");

    @Getter
    private final String value;

    CartItemSort(String value) {
        this.value = value;
    }
}
