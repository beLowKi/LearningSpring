package com.malcolm.learningspring.enums;

import lombok.Getter;

public enum CustomerSort {
    ID("id"),
    NAME("name"),
    EMAIL("email"),
    ADDRESS("address");

    @Getter
    private final String value;

    CustomerSort(String value) {
        this.value = value;
    }
}
