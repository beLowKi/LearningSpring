package com.malcolm.learningspring.entities;

import com.malcolm.learningspring.enums.OrderStatus;
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

    @Column(name = "customer_id", insertable = false, updatable = false)
    UUID customerId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, insertable = false, updatable = false,  length = 10)
    OrderStatus status = OrderStatus.ONGOING;

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
