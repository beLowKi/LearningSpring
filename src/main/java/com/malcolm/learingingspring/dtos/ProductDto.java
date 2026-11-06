package com.malcolm.learingingspring.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
public class ProductDto {
    private UUID id;
    private String name;
    private String about;
    private Double price;
    private Set<CategoryDto> categories;
    private Set<ReviewDto> reviews;
}
