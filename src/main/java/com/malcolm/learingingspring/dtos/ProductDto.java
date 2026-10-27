package com.malcolm.learingingspring.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class ProductDto {
    private UUID id;
    private String name;
    private String about;
    private Double price;
    private Set<CategoryDto> categories;
}
