package com.xavierbouclet.adventofcode;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private List<Customer> customers;

    public CustomerController() {
        customers = new ArrayList<>(List.of(new Customer(UUID.randomUUID(), "John", "Doe")));
    }

    @GetMapping()
    public List<Customer> getCustomers() {
        return this.customers;
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable UUID id) {
        return this.customers.stream().filter(p -> p.id().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("Not Found"));
    }

    @PostMapping()
    public Customer createCustomer(@RequestBody Customer customer) {

        var customerToCreate = new Customer(UUID.randomUUID(), customer.firstName(), customer.lastName());

        this.customers.add(customerToCreate);

        return customerToCreate;
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable UUID id){
        this.customers.removeIf(person->person.id().equals(id));
        return "deleted";
    }







}
