package com.malcolm.learingingspring.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UpdateMode {
    MERGE("merge"),
    REPLACE("replace");

    private final String value;

    UpdateMode(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
