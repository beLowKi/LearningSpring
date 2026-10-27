package com.malcolm.learingingspring.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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

    @ManyToMany(
            mappedBy = "products",
            cascade = { CascadeType.PERSIST }
    )
    @Builder.Default
    Set<Category> categories =  new HashSet<>();

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
    }
}
