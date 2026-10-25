package com.malcolm.learingingspring.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "catalogue_items")
public class CatalogueItem {
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
}
