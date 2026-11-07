package com.malcolm.learningspring.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "about", nullable = false, length = 1000)
    String about;

    @Column(
            name = "price",
            nullable = false,
            check = @CheckConstraint(constraint = "price >= 0.0")
    )
    Double price;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @Builder.Default
    Set<Category> categories =  new HashSet<>();

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
    }

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    Set<CartItem> cartItems = new HashSet<>();

    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
        cartItem.setProduct(this);
    }

    public void removeCartItem(CartItem cartItem) {
        cartItems.remove(cartItem);
    }

    @OneToMany(
            mappedBy = "product",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    Set<OrderItem> orderItems = new HashSet<>();

    public void addOrder(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setProduct(this);
    }

    public void removeOrder(OrderItem orderItem) {
        orderItems.remove(orderItem);
    }

    @OneToMany(
            mappedBy = "product",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    Set<Review> reviews = new HashSet<>();

    public void addReview(Review review) {
        reviews.add(review);
        review.setProduct(this);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
    }
}
