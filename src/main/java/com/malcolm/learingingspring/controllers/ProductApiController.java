package com.malcolm.learingingspring.controllers;

import com.malcolm.learingingspring.dtos.ProductDto;
import com.malcolm.learingingspring.entities.Product;
import com.malcolm.learingingspring.mappers.ProductMapper;
import com.malcolm.learingingspring.repositories.ProductRepository;
import com.malcolm.learingingspring.requests.QueryProductsRequest;
import com.malcolm.learingingspring.specifications.ProductSpecifications;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductApiController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    /**
     * Returns list of Products. TBD making this a post request.
     * @return List of Product info
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductDto>> queryProducts(@RequestBody QueryProductsRequest req) {

        var specs = ProductSpecifications.withFilters(
                req.getCategoryIds(),
                req.getMinPrice(), req.getMaxPrice()
        );

        // TODO create PageRequest using skip and limit

        return ResponseEntity.ok(
                productRepository.findAll(specs, Sort.by(req.getSortBy().getValue()))
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

    /**
     * Creates a new Product.
     *
     * @return Newly created Product
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        Product product;

        try {
            product = productMapper.fromDto(productDto);
        } catch (Exception e) {
            // TODO more informative response?
            return ResponseEntity.badRequest().build();
        }

        productRepository.save(product);
        return ResponseEntity.ok(productMapper.toDto(product));
    }
}
