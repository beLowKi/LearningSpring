package com.malcolm.learningspring.enums;

import lombok.Getter;

public enum OrderStatus {
    ONGOING("ONGOING"),
    COMPLETE("COMPLETE"),
    CANCELLED("CANCELLED");

    @Getter
    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }
}
