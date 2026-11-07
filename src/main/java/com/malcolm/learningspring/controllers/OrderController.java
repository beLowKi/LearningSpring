package com.malcolm.learningspring.controllers;

import com.malcolm.learningspring.dtos.CartItemDto;
import com.malcolm.learningspring.dtos.OrderDto;
import com.malcolm.learningspring.entities.*;
import com.malcolm.learningspring.enums.OrderInclude;
import com.malcolm.learningspring.enums.OrderSort;
import com.malcolm.learningspring.enums.OrderStatus;
import com.malcolm.learningspring.mappers.OrderMapper;
import com.malcolm.learningspring.repositories.CustomerRepository;
import com.malcolm.learningspring.repositories.OrderRepository;
import com.malcolm.learningspring.repositories.ProductRepository;
import com.malcolm.learningspring.requests.CreateOrderRequest;
import com.malcolm.learningspring.specifications.OrderSpecifications;
import com.malcolm.learningspring.utils.Const;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    /**
     * Returns a list of all Orders.
     *
     * @param sort Optional sort field
     * @param asc Sorts in ascending order if true
     * @param skip Optional number of Orders to skip
     * @param limit Optional return limit; capped at 250
     * @param statuses Optional target OrderStatuses
     * @return List of Order info
     */
    @GetMapping(path = {"", "/"})
    public ResponseEntity<List<OrderDto>> getOrders(
            @RequestParam(required = false, defaultValue = "ID", name = "sort") OrderSort sort,
            @RequestParam(required = false, defaultValue = "true", name = "asc") Boolean asc,
            @RequestParam(required = false, defaultValue = "0", name = "skip")  Integer skip,
            @RequestParam(required = false, defaultValue = "250", name = "limit") Integer limit,
            @RequestParam(required = false, defaultValue = "", name = "includes") Set<OrderInclude> includes,
            @RequestParam(
                    required = false,
                    defaultValue = "ONGOING,CANCELLED,COMPLETE",
                    name = "statuses"
            ) Set<OrderStatus> statuses
    ) {
        // Catching invalid skip and limit
        if (skip < 0) return ResponseEntity.badRequest().build();
        if (limit <= 0) return ResponseEntity.badRequest().build();

        // Capping limit to max page size
        limit = Math.min(limit, Const.PAGE_LIMIT);

        // Creating page request
        Pageable page = PageRequest.of(
                skip / limit,
                limit,
                Sort.by(
                        (asc) ? Sort.Direction.ASC : Sort.Direction.DESC,
                        sort.getValue()
                )
        );

        // Set of inclusions
        Set<String> incl = new HashSet<>(includes.stream().map(OrderInclude::getValue).toList());

        try {
            return ResponseEntity.ok(
                    orderRepository.findAll(OrderSpecifications.byStatus(statuses), page)
                            .stream()
                            .map(o -> orderMapper.toDto(o, incl))
                            .toList()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    /**
     * Returns a single Order's info. Optionally, can include Customer and/or OrderItem details.
     * @param id Target Order ID
     * @param includes Optional inclusions. Note that each one involves a join with another table which
     *                 may increase the time of the operation.
     * @return Order info
     */
    @GetMapping(path = {"/{id}", "/{id}/"})
    public ResponseEntity<OrderDto> getOrder(
            @PathVariable UUID id,
            @RequestParam(required = false, defaultValue = "", name = "includes") Set<OrderInclude> includes
    ) {
        try {
            Order order = orderRepository.findById(id).orElse(null);
            if (order == null) return ResponseEntity.notFound().build();

            return ResponseEntity.ok(
                    orderMapper.toDto(
                        order,
                        new HashSet<>(includes.stream().map(OrderInclude::getValue).toList())
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    /**
     * Creates a new ongoing Order. TODO, If a Customer ID isn't provided, create a temp for anon order.
     * @param req Incoming request
     * @return Created Order info
     */
    @PostMapping(path = {"/new", "/new/"})
    public ResponseEntity<OrderDto> addOrder(@RequestBody CreateOrderRequest req) {

        Order order = new Order();

        // Validating Customer ID for non-anonymous Order
        if (req.getCustomerId() != null) {
            Customer customer = customerRepository.findById(req.getCustomerId()).orElse(null);

            // TODO more informative message saying Customer does not exist
            if (customer == null) return ResponseEntity.notFound().build();

            order.setCustomerId(req.getCustomerId());
        }

        order.setStatus(OrderStatus.ONGOING);
        order.setShippingAddressSnapshot(req.getAddress());
        order.setCreatedAt(Timestamp.from(Instant.now()));

        // Converting cart to OrderItems
        for (CartItemDto cartItem : req.getItems()) {
            Product product = productRepository.findById(cartItem.getProductId()).orElse(null);

            // TODO/TBD more informative message and/or handling of when some products aren't found
            if (product == null) return ResponseEntity.notFound().build();

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(product.getPrice());

            order.addOrderItem(orderItem);
        }

        try {
            orderRepository.save(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(orderMapper.toDto(order, Set.of("items")));
    }


    /**
     * Deletes an Order.
     * @param id Target Order ID
     * @return Deleted Order's info.
     */
    @DeleteMapping(path = {"/{id}/delete", "/{id}/delete/"})
    public ResponseEntity<OrderDto> deleteOrder(@PathVariable UUID id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) return ResponseEntity.notFound().build();

        try {
            orderRepository.delete(order);
        }  catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(orderMapper.toDto(order, Set.of()));
    }


    /**
     * Updates the status of an Order.
     * @param id Target Order ID
     * @param status Target OrderStatus
     * @return Updated Order info
     */
    @PutMapping(path = {"/{id}", "/{id}/"})
    public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable UUID id, @RequestParam OrderStatus status) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) return ResponseEntity.notFound().build();

        order.setStatus(status);
        try {
            orderRepository.save(order);
        }  catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(orderMapper.toDto(order, Set.of()));
    }
}
