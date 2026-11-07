package com.malcolm.learningspring.entities;

import com.malcolm.learningspring.utils.models.CustomerProductId;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(CustomerProductId.class)
@Table(name = "reviews")
public class Review {
    @Id
    @Column(name = "customer_id", insertable = false, updatable = false)
    private UUID customerId;

    @Id
    @Column(name = "product_id", nullable = false, insertable = false, updatable = false)
    private UUID productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(
            name = "rating",
            nullable = false,
            check = @CheckConstraint(constraint = "rating >= 1")
    )
    private Double rating;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "message", nullable = false, length = 1000)
    private String message;
}
