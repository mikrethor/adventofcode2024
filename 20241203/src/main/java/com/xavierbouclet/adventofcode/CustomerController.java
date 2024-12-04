package com.xavierbouclet.adventofcode;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Customer>> getAll() {
        return  ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(customers.stream().filter(c -> c.id().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("Not Found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id) {
        customers.removeIf(c -> c.id().equals(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping()
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        customers.add(customer);
        return ResponseEntity.status(200).body(customer);
    }
}
