package com.malcolm.learingingspring.controllers;

import com.malcolm.learingingspring.dtos.ProductDto;
import com.malcolm.learingingspring.entities.Product;
import com.malcolm.learingingspring.mappers.ProductMapper;
import com.malcolm.learingingspring.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/products/")
public class ProductApiController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    /**
     * Returns list of Products.
     * @param sort Sorts returned list by "name"|"price"
     * @param categoryIds List of Category id(s) to filter for
     * @return List of Product info
     */
    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts(
            @RequestParam(required = false, defaultValue = "", name = "sort") String sort,
            @RequestParam(required = false, defaultValue = "", name = "categories") Set<UUID> categoryIds,
            @RequestParam(required = false, defaultValue = "", name = "minPrice") double minPrice,
            @RequestParam(required = false, defaultValue = "", name = "maxPrice") double maxPrice
    ) {

        // Defaults to 'name' sorting
        if ( !Set.of("name", "price").contains(sort) ) {
            sort = "name";
        }

        return ResponseEntity.ok(
                productRepository.findAllIn(categoryIds, minPrice, maxPrice)
                        .stream()
                        .map(productMapper::toDto)
                        .toList()
        );
    }

    /**
     * Returns a single Product record by id.
     * @return Response containing Product info.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(
            @PathVariable UUID id
    ) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(productMapper.toDto(product));
    }
}
