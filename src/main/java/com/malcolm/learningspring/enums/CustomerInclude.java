package com.malcolm.learningspring.enums;

import lombok.Getter;

public enum CustomerInclude {
    CART_ITEMS("cartItems"),
    ORDERS("orders"),
    REVIEWS("reviews");

    @Getter
    private final String value;

    CustomerInclude(String value) {
        this.value = value;
    }
}
