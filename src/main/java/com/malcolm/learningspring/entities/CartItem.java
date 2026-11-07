package com.malcolm.learningspring.entities;

import com.malcolm.learningspring.utils.models.CustomerProductId;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@IdClass(CustomerProductId.class)
@Table(name = "cart_items")
public class CartItem {
    @Id
    @Column(name = "customer_id")
    private UUID customerId;

    @Id
    @Column(name = "product_id")
    private UUID productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(
            name = "quantity",
            nullable = false,
            check = @CheckConstraint(constraint = "quantity >= 1")
    )
    private int quantity;
}
