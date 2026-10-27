package com.malcolm.learingingspring.requests;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Value;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Value
public class QueryProductsRequest {
    public enum SortBy {
        NAME("NAME"),
        PRICE("PRICE");

        private final String value;

        SortBy(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }
    }

    SortBy sortBy = SortBy.NAME;
    Integer skip = 0;
    Integer limit = 250;
    Set<UUID> categoryIds = new HashSet<>();
    Double minPrice = null;
    Double maxPrice = null;
}
