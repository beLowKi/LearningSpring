package com.malcolm.learingingspring.entities;

import com.malcolm.learingingspring.utils.models.OrderItemId;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@IdClass(OrderItemId.class)
@Table(name = "order_items")
public class OrderItem {
    @Id
    @Column(name = "order_id")
    private UUID orderId;

    @Id
    @Column(name = "catalogue_item_id")
    private UUID catalogueItemId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "catalogue_item_id", nullable = false)
    private CatalogueItem catalogueItem;

    @Column(
            name = "quantity",
            nullable = false,
            check = @CheckConstraint(constraint = "quantity >= 1")
    )
    private int quantity;

    @Column(
            name = "unit_price",
            nullable = false,
            check = @CheckConstraint(constraint = "unit_price >= 0.0")
    )
    private double unitPrice;
}
