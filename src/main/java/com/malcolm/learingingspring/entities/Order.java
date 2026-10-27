package com.malcolm.learingingspring.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    Customer customer;

    @Column(name = "shipping_address_snapshot", nullable = false)
    String shippingAddressSnapshot;

    @Column(name = "created_at",  nullable = false)
    Timestamp createdAt;

    @OneToMany(
            mappedBy = "order",
            cascade = { CascadeType.PERSIST, CascadeType.REMOVE },
            orphanRemoval = true
    )
    @Builder.Default
    Set<OrderItem> orderItems = new HashSet<>();

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
        orderItem.setOrder(null);
    }
}
