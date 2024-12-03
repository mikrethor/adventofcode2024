package com.xavierbouclet.adventofcode;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private List<Customer> customers = new ArrayList<>(List.of(
            new Customer(UUID.randomUUID(), "Santa", "Claus"),
            new Customer(UUID.randomUUID(), "Maria", "Carey")));

    @GetMapping
    public List<Customer> getAll() {
        return customers;
    }

    @GetMapping("/{id}")
    public Customer getById(@PathVariable("id") UUID id) {
        return customers.stream().filter(c -> c.id().equals(id)).findFirst().get();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") UUID id) {
        customers.removeIf(c -> c.id().equals(id));
    }

    @PostMapping()
    public Customer createCustomer(@RequestBody Customer customer) {
        customers.add(customer);
    }

}
