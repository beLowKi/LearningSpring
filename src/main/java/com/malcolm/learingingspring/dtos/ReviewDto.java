package com.malcolm.learingingspring.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
public class ReviewDto {
    private Long id;
    private UUID productId;
    private UUID customerId;
    private Double rating;
    private String title;
    private String message;
}
