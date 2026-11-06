package com.malcolm.learingingspring.controllers;

import com.malcolm.learingingspring.dtos.CategoryDto;
import com.malcolm.learingingspring.dtos.ProductDto;
import com.malcolm.learingingspring.dtos.ReviewDto;
import com.malcolm.learingingspring.entities.Category;
import com.malcolm.learingingspring.entities.Product;
import com.malcolm.learingingspring.entities.Review;
import com.malcolm.learingingspring.enums.UpdateMode;
import com.malcolm.learingingspring.mappers.CategoryMapper;
import com.malcolm.learingingspring.mappers.ProductMapper;
import com.malcolm.learingingspring.mappers.ReviewMapper;
import com.malcolm.learingingspring.repositories.CategoryRepository;
import com.malcolm.learingingspring.repositories.ProductRepository;
import com.malcolm.learingingspring.requests.SearchProductReviewsRequest;
import com.malcolm.learingingspring.requests.SearchProductsRequest;
import com.malcolm.learingingspring.specifications.ProductSpecifications;

import com.malcolm.learingingspring.utils.Const;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductApiController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ReviewMapper reviewMapper;

    /**
     * Returns a list of all Products.
     *
     * @param includes List of join properties to include
     * @param sort Optional sorting field
     * @param asc Sets sorting to ascending when true
     * @return List of Product info
     */
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(
            @RequestParam(required = false, defaultValue = "", name = "includes") Set<String> includes,
            @RequestParam(required = false, defaultValue = "", name = "sort") String sort,
            @RequestParam(required = false, defaultValue = "true", name = "asc") Boolean asc,
            @RequestParam(required = false, defaultValue = "0", name = "skip") Integer skip,
            @RequestParam(required = false, defaultValue = "250", name = "limit") Integer limit
    ) {

        // Defaults sorting to id
        if ( !Set.of("name", "price", "id").contains(sort) ) {
            sort = "id";
        }

        // Defaults invalid skip and limit
        if (skip < 0) skip = 0;
        if (limit < 0 || limit > Const.PAGE_LIMIT) limit = Const.PAGE_LIMIT;

        // Create PageRequest
        Pageable pageable = PageRequest.of(
                skip / limit,
                limit,
                Sort.by(
                        (asc) ? Sort.Direction.ASC : Sort.Direction.DESC,
                        sort
                )
        );

        return ResponseEntity.ok(
                productRepository.findAll(pageable)
                        .stream()
                        .map(product -> productMapper.toDto(product, includes))
                        .toList()
        );
    }


    /**
     * Returns list of Products. TBD making this a post request.
     * @return List of Product info
     */
    @PostMapping(path="/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductDto>> searchProducts(@RequestBody SearchProductsRequest req) {

        // Defaults invalid skip and limit
        if (req.getSkip() < 0) req.setSkip(0);
        if (req.getLimit() < 0 || req.getLimit() > Const.PAGE_LIMIT) req.setLimit(Const.PAGE_LIMIT);

        // Creating specs required to filter the search
        Specification<Product> specs = ProductSpecifications.withFilters(
                req.getCategoryIds(),
                req.getMinPrice(), req.getMaxPrice()
        );

        // Creating PageRequest
        Pageable pageable = PageRequest.of(
                req.getSkip() / req.getLimit(),
                req.getLimit(),
                Sort.by(
                        req.getSortDirection(),
                        req.getSortBy().getValue()
                )
        );

        try {
            return ResponseEntity.ok(
                    productRepository.findAll(specs, pageable)
                            .stream()
                            .map(product -> productMapper.toDto(product, req.getIncludes()))
                            .toList()
            );
        } catch (Exception e) {
            // TODO more informative response?
            return ResponseEntity.badRequest().build();
        }
    }


    /**
     * Returns a single Product record by id.
     * @return Response containing Product info.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(
            @PathVariable UUID id,
            @RequestParam(required = false, defaultValue = "", name = "include") Set<String> includes
    ) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(productMapper.toDto(product, includes));
    }

    /**
     * Creates a new Product.
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
        return ResponseEntity.ok(productMapper.toDto(product, Set.of()));
    }


    /**
     * Updates a Product's info. Optionally, can create a new Product if one with the given ID isn't found.
     * @param id Target Product ID
     * @param productDto New Product info
     * @param createIfNotExist When true, a new Product is created if there isn't one with the given ID.
     * @param mode Sets the update mode. MERGE only affects properties named in the request body whereas REPLACE
     *             will set all unnamed properties other than id to null or its equivalent.
     * @return New Product info
     */
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable UUID id,
            @RequestBody ProductDto productDto,
            @RequestParam(required = false, defaultValue = "false", name = "createIfNotExist") boolean createIfNotExist,
            @RequestParam(required = false, defaultValue = "MERGE", name = "mode") UpdateMode mode
    ) {

        Product product = productRepository.findById(id).orElse(null);

        if ( product == null ) {

            // This param, when false, requires an existing Product
            if (!createIfNotExist) {
                return ResponseEntity.notFound().build();
            }

            product = productMapper.fromDto(productDto);

        // Updating existing product
        // TODO using mode to change how unnamed properties are handled
        } else {
            try {
                BeanUtils.copyProperties(productMapper.fromDto(productDto), product, "id");
            } catch (Exception e) {
                // TODO more informative response?
                return ResponseEntity.badRequest().build();
            }
        }

        try {
            productRepository.save(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(productMapper.toDto(product,  Set.of()));
    }


    /**
     * Deletes the Product with the given id if it exists.
     * @param id Target Product id
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID id) {
        Product product = productRepository.findById(id).orElse(null);
        if  (product == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            productRepository.delete(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }


    /**
     * Returns a list of all a Product's categories.
     * @param id Target Product ID
     * @return List of Category info
     */
    @GetMapping("/{id}/categories")
    public ResponseEntity<List<CategoryDto>> getCategories(@PathVariable UUID id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                product.getCategories()
                        .stream()
                        .map(categoryMapper::toDto)
                        .toList()
        );
    }


    /**
     * Adds the given categories to a Product. Both the product and the categories must already exist or this
     * will return 400.
     *
     * @param id Target Product ID
     * @param categoryIds Target Category IDs
     * @return Updated Product and its categories
     */
    @PostMapping(path = "/{id}/add-categories", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> addCategories(
            @PathVariable UUID id,
            @RequestBody Set<UUID> categoryIds
    ) {

        Product product = productRepository.findById(id).orElse(null);
        if ( product == null ) {
            return ResponseEntity.notFound().build();
        }

        Set<Category> categories = new HashSet<>();
        categoryRepository.findAllById(categoryIds).forEach(categories::add);

        // Mismatch size means one or more of the provided category IDs doesn't exist
        if ( categories.size() != categoryIds.size() ) {
            return ResponseEntity.badRequest().build();
        }

        try {
            product.getCategories().addAll(categories);
            productRepository.save(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(productMapper.toDto(product, Set.of("categories")));
    }


    /**
     * Removes the given categories from a Product. Non-existent Category IDs are ignored.
     *
     * @param id Target Product ID
     * @param categoryIds Target Category IDs
     * @return Updated Product and its categories
     */
    @PostMapping(path = "/{id}/remove-categories", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> removeCategories(
            @PathVariable UUID id,
            @RequestBody Set<UUID> categoryIds
    ) {

        Product product = productRepository.findById(id).orElse(null);
        if ( product == null ) {
            return ResponseEntity.notFound().build();
        }

        Set<Category> categories = product.getCategories();
        categories.removeIf(category -> categoryIds.contains(category.getId()));

        product.setCategories(categories);

        try {
            productRepository.save(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(productMapper.toDto(product, Set.of("categories")));
    }


    /**
     * Returns all of a Products reviews
     * @param id Target Product ID
     * @return List of Review info
     */
    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewDto>> getReviews(@PathVariable UUID id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                product.getReviews()
                        .stream()
                        .map(reviewMapper::toDto)
                        .toList()
        );
    }


    /**
     * Searches a Product's Reviews for ones matching certain criteria
     * @param id Target Product ID
     * @param req Request body containing search params
     * @return List of Review info
     */
    @PostMapping("/{id}/search-reviews")
    public ResponseEntity<List<ReviewDto>> searchReviews(
            @PathVariable UUID id,
            @RequestBody SearchProductReviewsRequest req
    ) {
        Product product = productRepository.findById(id).orElse(null);
        if ( product == null ) {
            return ResponseEntity.notFound().build();
        }

        Set<Review> reviews = product.getReviews();
        UUID customerId = req.getCustomerId();
        Double minRating = req.getMinRating();
        Double maxRating = req.getMaxRating();

        try {
            reviews.removeIf(review -> {
                Double rating = review.getRating();

                return (
                        ( customerId == null || customerId.equals(review.getCustomerId()) ) &&
                        ( minRating == null || Double.compare(rating, minRating) >= 0 ) &&
                        ( maxRating == null || Double.compare(rating, maxRating) <= 0 )
                );
            });

        } catch (Exception e) {
            return  ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(
                reviews
                        .stream()
                        .map(reviewMapper::toDto)
                        .toList()
        );
    }


    /**
     * Adds a Review for the Product with the given ID.
     * @param id Target Product ID
     * @param reviewDto Review info
     * @return Added Review
     */
    @PostMapping(path = "/{id}/add-review", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDto> addReview(
            @PathVariable UUID id,
            @RequestBody ReviewDto reviewDto
    ) {
        Product product = productRepository.findById(id).orElse(null);
        if ( product == null ) {
            return ResponseEntity.notFound().build();
        }

        Review review = reviewMapper.fromDto(reviewDto);
        try {
            product.addReview(review);
            productRepository.save(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(reviewDto);
    }


    /**
     * Deletes the given Customer's review from a Product.
     * @param productId Target Product ID
     * @param customerId Target Customer ID
     * @return Updated Product info
     */
    @DeleteMapping(path = "/{id}/remove-review/{customerId}")
    public ResponseEntity<ProductDto> removeReview(
            @PathVariable(name = "id") UUID productId,
            @PathVariable(name = "customerId") UUID customerId
    ) {
        Product product = productRepository.findById(productId).orElse(null);
        if ( product == null ) {
            return ResponseEntity.notFound().build();
        }

        try {
            Set<Review> reviews = product.getReviews();
            reviews.removeIf(r -> r.getCustomerId().equals(customerId));
            product.setReviews(reviews);
            productRepository.save(product);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(productMapper.toDto(product, Set.of()));
    }
}
