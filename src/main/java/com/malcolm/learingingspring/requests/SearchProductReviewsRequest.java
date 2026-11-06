package com.malcolm.learingingspring.requests;

import lombok.Data;

import java.util.UUID;

@Data
public class SearchProductReviewsRequest {
    UUID customerId;
    Double minRating = null;
    Double maxRating = null;
}
