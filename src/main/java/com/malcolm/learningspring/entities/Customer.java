package com.malcolm.learningspring.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    @OneToMany(
            mappedBy = "customer",
            cascade = { CascadeType.PERSIST, CascadeType.REMOVE },
            orphanRemoval = true
    )
    @Builder.Default
    private Set<CartItem> cartItems =  new HashSet<>();

    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
        cartItem.setCustomer(this);
    }

    public void removeCartItem(CartItem cartItem) {
        cartItems.remove(cartItem);
        cartItem.setCustomer(null);
    }

    @OneToMany(
            mappedBy = "customer",
            cascade = { CascadeType.PERSIST, CascadeType.REMOVE },
            orphanRemoval = true
    )
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
        order.setCustomer(this);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        order.setCustomer(null);
    }

    @OneToMany(
            mappedBy = "customer",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    Set<Review> reviews = new HashSet<>();

    public void addReview(Review review) {
        reviews.add(review);
        review.setCustomer(this);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
    }
}
