package com.malcolm.learingingspring.controllers;

import java.util.Set;
import java.util.UUID;

import com.malcolm.learingingspring.dtos.CustomerDto;
import com.malcolm.learingingspring.mappers.CustomerMapper;
import com.malcolm.learingingspring.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.malcolm.learingingspring.entities.Customer;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerApiController {

    // TODO more informative 400 messages; e.g., explaining which param is incorrect and why

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    // FIXME Requesting to "...api/v1/customers (without ending /) fails
    // GET needs to resolve to this method with or without ending /
    /**
     * Returns list of all Customers
     * @return Customer
     */
    @GetMapping
    public ResponseEntity<Iterable<CustomerDto>> getAllCustomers(
            @RequestParam(required = false, defaultValue = "", name = "sort") String sort
    ) {

        // Invalid sort param defaults to name
        if ( !Set.of("name", "email").contains(sort) ) {
            sort = "name";
        }

        return ResponseEntity.ok(
                customerRepository.findAll(Sort.by(sort))
                        .stream()
                        .map(customerMapper::toDto)
                        .toList()
        );
    }

    /**
     * Returns a single Customer's information
     * @return Customer
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable UUID id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customerMapper.toDto(customer));
    }

    /**
     * Adds a new Customer
     * @return new Customer info
     */
    @PostMapping("/{id}")
    public Customer addCustomer(@PathVariable Integer id, @RequestBody Customer customer) {

        // TODO validate; returning valid 400 message if failed

        return customerRepository.save(customer);
    }

    /**
     * Updates a Customer record
     * @return updated Customer info
     */
    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable UUID id, @RequestBody Customer customer) {
        // TODO validate; returning valid 400 message if failed

        return customerRepository.save(customer);
    }
}
