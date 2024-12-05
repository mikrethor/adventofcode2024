package com.xavierbouclet.adventofcode;

import jakarta.servlet.ServletException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CustomerHandler {

    private List<Customer> customers = new ArrayList<>(List.of(
            new Customer(UUID.randomUUID(), "Santa", "Claus"),
            new Customer(UUID.randomUUID(), "Maria", "Carey")));

    public ServerResponse getCustomers() {
        return ServerResponse.ok().body(this.customers);
    }

    public ServerResponse getCustomer(ServerRequest request) {
        UUID id = UUID.fromString(request.pathVariable("id"));
        return ServerResponse.ok().body(this.customers.stream().filter(p -> p.id().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("Not Found")));
    }

    public ServerResponse createCustomer(ServerRequest request) throws ServletException, IOException {
        Customer customer = request.body(Customer.class);

        var customerToCreate = new Customer(UUID.randomUUID(), customer.firstName(), customer.lastName());

        this.customers.add(customerToCreate);

        return ServerResponse.ok().body(customerToCreate);
    }

    public ServerResponse deleteCustomer(ServerRequest request) {
        UUID id = UUID.fromString(request.pathVariable("id"));
        this.customers.removeIf(person -> person.id().equals(id));
        return ServerResponse.noContent().build();
    }

}
