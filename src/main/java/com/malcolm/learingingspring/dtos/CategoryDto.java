package com.malcolm.learingingspring.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class CategoryDto {
    UUID id;
    String name;
}
