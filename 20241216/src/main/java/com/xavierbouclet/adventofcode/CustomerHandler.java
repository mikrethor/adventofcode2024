package com.xavierbouclet.adventofcode;

import jakarta.servlet.ServletException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.io.IOException;
import java.util.UUID;

@Component
public class CustomerHandler {


    private CustomerRepository customerRepository;

    public CustomerHandler(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public ServerResponse getAll() {
        return ServerResponse.ok().body(customerRepository.findAll());
    }

    public ServerResponse getById(ServerRequest request) {
        UUID id = UUID.fromString(request.pathVariable("id"));
        return ServerResponse.ok().body(customerRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    public ServerResponse deleteById(ServerRequest request) {
        UUID id = UUID.fromString(request.pathVariable("id"));

        customerRepository.deleteById(id);

        return ServerResponse.noContent().build();
    }

    public ServerResponse createCustomer(ServerRequest request) throws ServletException, IOException {
        Customer customer = request.body(Customer.class);

        var customerCreated=customerRepository.save(customer);

        return ServerResponse.ok().body(customerCreated);
    }
}
