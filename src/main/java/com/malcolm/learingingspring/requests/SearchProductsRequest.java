package com.malcolm.learingingspring.requests;

import com.fasterxml.jackson.annotation.JsonValue;
import com.malcolm.learingingspring.utils.Const;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class SearchProductsRequest {
    public enum SortBy {
        ID("id"),
        NAME("name"),
        PRICE("price");

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
    Sort.Direction sortDirection = Sort.Direction.ASC;
    Integer skip = 0;
    Integer limit = Const.PAGE_LIMIT;
    Set<UUID> categoryIds = new HashSet<>();
    Set<String> includes = new HashSet<>();
    Double minPrice = null;
    Double maxPrice = null;
}
