package com.malcolm.learingingspring.entities;

import com.malcolm.learingingspring.utils.models.CartItemId;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@IdClass(CartItemId.class)
@Table(name = "cart_items")
public class CartItem {
    @Id
    @Column(name = "customer_id")
    private UUID customerId;

    @Id
    @Column(name = "catalogue_item_id")
    private UUID catalogueItemId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "catalogue_item_id", nullable = false)
    private CatalogueItem item;

    @Column(
            name = "quantity",
            nullable = false,
            check = @CheckConstraint(constraint = "quantity >= 1")
    )
    private int quantity;
}
