package com.malcolm.learningspring.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import com.malcolm.learningspring.dtos.CartItemDto;
import com.malcolm.learningspring.dtos.CustomerDto;
import com.malcolm.learningspring.dtos.OrderDto;
import com.malcolm.learningspring.dtos.OrderItemDto;
import com.malcolm.learningspring.entities.CartItem;
import com.malcolm.learningspring.entities.Order;
import com.malcolm.learningspring.entities.Product;
import com.malcolm.learningspring.enums.*;
import com.malcolm.learningspring.mappers.CartItemMapper;
import com.malcolm.learningspring.mappers.CustomerMapper;
import com.malcolm.learningspring.mappers.OrderMapper;
import com.malcolm.learningspring.repositories.CartItemRepository;
import com.malcolm.learningspring.repositories.CustomerRepository;
import com.malcolm.learningspring.repositories.OrderRepository;
import com.malcolm.learningspring.repositories.ProductRepository;
import com.malcolm.learningspring.specifications.OrderSpecifications;
import com.malcolm.learningspring.utils.Const;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.malcolm.learningspring.entities.Customer;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerApiController {

    // TODO more informative 400 messages; e.g., explaining which param is incorrect and why

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    private final ProductRepository productRepository;

    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;


    // FIXME Requesting to "...api/v1/customers (without ending /) fails
    // GET needs to resolve to this method with or without ending /
    /**
     * Returns list of all Customers
     * @return Customer
     */
    @GetMapping(path = {"/", ""})
    public ResponseEntity<List<CustomerDto>> getAllCustomers(
            @RequestParam(required = false, defaultValue = "ID", name = "sort") CustomerSort sort,
            @RequestParam(required = false, defaultValue = "", name = "includes") Set<CustomerInclude> includes
    ) {

        Set<String> includeList = new HashSet<>(
                includes
                    .stream()
                    .map(CustomerInclude::getValue).toList()
        );

        Function<Customer, CustomerDto> mapper = c -> customerMapper.toDto(c, includeList);

        try {
            return ResponseEntity.ok(
                    customerRepository.findAll(Sort.by(sort.getValue()))
                            .stream()
                            .map(mapper)
                            .toList()
            );
        } catch (Exception e) {
            return  ResponseEntity.badRequest().build();
        }
    }


    /**
     * Returns a single Customer's information
     * @return Customer
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomer(
            @PathVariable UUID id,
            @RequestParam(required = false, defaultValue = "", name = "includes") Set<CustomerInclude> includes
    ) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        Set<String> includeList = new HashSet<>(
                includes
                        .stream()
                        .map(CustomerInclude::getValue).toList()
        );

        try {
            return ResponseEntity.ok(customerMapper.toDto(customer, includeList));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    /**
     * Adds a new Customer
     * @return new Customer info
     */
    @PostMapping("/{id}")
    public ResponseEntity<CustomerDto> addCustomer(@PathVariable Integer id, @RequestBody CustomerDto customerDto) {
        Customer customer = customerMapper.fromDto(customerDto);

        try {
            customerRepository.save(customer);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(customerDto);
    }


    /**
     * Updates a Customer record
     * @return updated Customer info
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(
            @PathVariable UUID id,
            @RequestBody CustomerDto customerDto,
            @RequestParam(required = false, defaultValue = "false", name = "createIfNotExist")  Boolean createIfNotExist,
            @RequestParam(required = false, defaultValue = "merge", name = "mode") UpdateMode mode
    ) {
        Customer customer = customerRepository.findById(id).orElse(null);

        if ( customer == null ) {

            // This param, when false, requires an existing Customer
            if (!createIfNotExist) {
                return ResponseEntity.notFound().build();
            }

            customer = customerMapper.fromDto(customerDto);

            // Updating existing Customer
            // TODO using mode to change how unnamed properties are handled
        } else {
            try {
                BeanUtils.copyProperties(customerMapper.fromDto(customerDto), customer, "id");
            } catch (Exception e) {
                // TODO more informative response?
                return ResponseEntity.badRequest().build();
            }
        }

        try {
            customerRepository.save(customer);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(customerDto);
    }


    /**
     * Deletes the Customer with the given ID.
     * @param id Target Customer ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable UUID id) {
        Customer customer = customerRepository.findById(id).orElse(null);

        if  (customer == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            customerRepository.delete(customer);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }


    /**
     * Returns all the items in a Customer's Cart.
     *
     * @param id Target Customer ID
     * @param sort Determines how CartItems are sorted
     * @return List of CartItem info
     */
    @GetMapping(path = {"/{id}/cart", "/{id}/cart/"})
    public ResponseEntity<List<CartItemDto>> getCartItems(
            @PathVariable UUID id,
            @RequestParam(required = false, defaultValue = "PRODUCT_ID", name = "sort") CartItemSort sort
    ) {
        try {
            return ResponseEntity.ok(
                    cartItemRepository.findAllByCustomerId(id, Sort.by(sort.getValue()))
                            .stream()
                            .map(cartItemMapper::toDto)
                            .toList()
            );

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    /**
     * Adds a Product to a Customer's cart.
     *
     * @param id Target Customer ID
     * @param productId Target Product ID
     * @param quantity Initial quantity of item
     * @return Customer whose cart was added to
     */
    @PostMapping("/{id}/add-to-cart")
    public ResponseEntity<CustomerDto> addToCart(
            @PathVariable UUID id,
            @RequestParam(name = "productId") UUID productId,
            @RequestParam(required = false, defaultValue = "1", name = "quantity") Integer quantity
    ) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if ( customer == null ) {
            return ResponseEntity.notFound().build();
        }

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            CartItem cartItem = new CartItem(customer.getId(), product.getId(), customer, product, quantity);
            customer.getCartItems().add(cartItem);
            customerRepository.save(customer);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(customerMapper.toDto(customer, Set.of()));
    }


    /**
     * Removes the given Product from a Customer's cart.
     *
     * @param id Target Customer ID
     * @param productId Target Product ID
     * @return Updated Customer info
     */
    @DeleteMapping(path = {"/{id}/remove-from-cart", "/{id}/remove-from-cart/"})
    public ResponseEntity<CustomerDto> removeFromCart(
            @PathVariable UUID id,
            @RequestParam(name = "productId") UUID productId
    ) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if ( customer == null ) {
            return ResponseEntity.notFound().build();
        }

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        Set<CartItem> cart =  customer.getCartItems();
        try {
            // 404 also returned if target Product ID doesn't exist in cart
            if ( !cart.removeIf(ci -> ci.getProductId().equals(productId)) ) {
                return ResponseEntity.notFound().build();
            }

            customer.setCartItems(cart);
            customerRepository.save(customer);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(customerMapper.toDto(customer, Set.of()));
    }


    /**
     * Updates the quantity of the given Product inside a Customer's cart.
     *
     * @param id Target Customer ID
     * @param productId Target Product ID
     * @param quantity New quantity
     * @return Updated Customer info including cart items
     */
    @PutMapping(path = {"/{id}/update-cart-item-quantity", "/{id}/update-cart-item-quantity/"})
    public ResponseEntity<CustomerDto> updateCartItemQuantity(
            @PathVariable UUID id,
            @RequestParam(name = "productId") UUID productId,
            @RequestParam(name = "quantity") Integer quantity
    ) {

        // Blocking invalid quantities
        if (quantity <= 0) return ResponseEntity.badRequest().build();

        Customer customer = customerRepository.findById(id).orElse(null);
        if ( customer == null ) {
            return ResponseEntity.notFound().build();
        }

        Set<CartItem> cart =  customer.getCartItems();
        boolean found = false;

        for (CartItem cartItem : cart) {
            if (cartItem.getProductId().equals(productId)) {
                cartItem.setQuantity(quantity);
                found = true;
                break;
            }
        }

        if  (!found) return ResponseEntity.notFound().build();

        customer.setCartItems(cart);
        customerRepository.save(customer);

        return ResponseEntity.ok(customerMapper.toDto(customer, Set.of("cartItems")));
    }


    /**
     * Returns of all a Customer's orders.
     * @param id Target Customer ID
     * @param sort Sorting for returned Orders
     * @return List of Order info
     */
    @GetMapping(path = {"/{id}/orders", "/{id}/orders/"})
    public ResponseEntity<List<OrderDto>> getOrders(
            @PathVariable UUID id,
            @RequestParam(
                    required = false,
                    defaultValue = "ONGOING,COMPLETE,CANCELLED",
                    name = "statuses"
            ) Set<OrderStatus> statuses,
            @RequestParam(required = false, defaultValue = "ID", name = "sort") OrderSort sort,
            @RequestParam(required = false, defaultValue = "0", name = "skip") Integer skip,
            @RequestParam(required = false, defaultValue = "250", name = "limit")  Integer limit
    ) {

        // Catching invalid skip or limit
        if (skip < 0) return ResponseEntity.badRequest().build();
        if (limit <= 0)  return ResponseEntity.badRequest().build();

        // Capping limit to max page size
        limit = Math.min(limit, Const.PAGE_LIMIT);

        Specification<Order> spec = OrderSpecifications.byStatus(statuses, id);
        Pageable page = PageRequest.of(skip, limit, Sort.by(sort.getValue()));

        try {
            return ResponseEntity.ok(
                    orderRepository.findAll(spec, page)
                            .stream()
                            .map(o -> orderMapper.toDto(o, Set.of()))
                            .toList()
            );

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
