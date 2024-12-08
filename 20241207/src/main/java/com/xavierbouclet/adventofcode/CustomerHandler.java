package com.xavierbouclet.adventofcode;

import jakarta.servlet.ServletException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.io.IOException;
import java.util.UUID;

@Component
public class CustomerHandler {

    private final CustomerRepository repository;

    public CustomerHandler(CustomerRepository repository) {
        this.repository = repository;
    }

    public ServerResponse getCustomers() {
        return ServerResponse.ok().body(repository.findAll());
    }

    public ServerResponse getCustomer(ServerRequest request) {
        UUID id = UUID.fromString(request.pathVariable("id"));
        return ServerResponse.ok().body(repository.findCustomerById(id).orElseThrow(ResourceNotFoundException::new));
    }

    public ServerResponse createCustomer(ServerRequest request) throws ServletException, IOException {
        Customer customer = request.body(Customer.class);

        var customerToCreate = new Customer(UUID.randomUUID(), customer.getFirstName(), customer.getLastName());

        repository.save(customerToCreate);

        return ServerResponse.ok().body(customerToCreate);
    }

    public ServerResponse deleteCustomer(ServerRequest request) {
        UUID id = UUID.fromString(request.pathVariable("id"));
        repository.deleteById(id);
        return ServerResponse.noContent().build();
    }

}
