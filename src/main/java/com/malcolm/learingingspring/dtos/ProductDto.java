package com.malcolm.learingingspring.dtos;

import com.malcolm.learingingspring.entities.Category;
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
    private double price;
    private Set<Category> categories;
}
