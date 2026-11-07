package com.malcolm.learningspring.entities;

import com.malcolm.learningspring.utils.models.OrderItemId;

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
    @Column(name = "product_id")
    private UUID productId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

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
