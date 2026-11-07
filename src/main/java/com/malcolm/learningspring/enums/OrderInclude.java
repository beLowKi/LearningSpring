package com.malcolm.learningspring.enums;

import lombok.Getter;

public enum OrderInclude {
    ITEMS("orderItems"),
    CUSTOMER("customer");

    @Getter
    private final String value;

    OrderInclude(String value) {
        this.value = value;
    }
}
