package com.xavierbouclet.adventofcode;

import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration(proxyBeanMethods = false)
public class RouterConfiguration {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/customers/{id}",
                    beanClass = CustomerHandler.class,
                    beanMethod = "getCustomer",
                    operation = @io.swagger.v3.oas.annotations.Operation(
                            summary = "Get customer by ID",
                            description = "Retrieve a specific customer using their ID",
                            tags = {"Customers"}
                    )
            ),
            @RouterOperation(
                    path = "/api/customers/{id}",
                    beanClass = CustomerHandler.class,
                    beanMethod = "deleteCustomer",
                    operation = @io.swagger.v3.oas.annotations.Operation(
                            summary = "Delete customer by ID",
                            description = "Delete a specific customer using their ID",
                            tags = {"Customers"}
                    )
            ),
            @RouterOperation(
                    path = "/api/customers",
                    beanClass = CustomerHandler.class,
                    beanMethod = "getCustomers",
                    operation = @io.swagger.v3.oas.annotations.Operation(
                            summary = "List all customers",
                            description = "Retrieve a list of all customers",
                            tags = {"Customers"}
                    )
            ),
            @RouterOperation(
                    path = "/api/customers",
                    beanClass = CustomerHandler.class,
                    beanMethod = "createCustomer",
                    operation = @io.swagger.v3.oas.annotations.Operation(
                            summary = "Create a new customer",
                            description = "Create a new customer with the given details",
                            tags = {"Customers"}
                    )
            )
    })
    public RouterFunction<ServerResponse> customerRouter(CustomerHandler customerHandler) {
        return route()
                .path("/api/customers/{id}", builder -> builder
                        .GET(customerHandler::getCustomer)
                        .DELETE(customerHandler::deleteCustomer)
                )
                .path("/api/customers", builder -> builder
                        .GET(req -> customerHandler.getCustomers())
                        .POST(customerHandler::createCustomer)
                )
                .filter(new GlobalErrorHandler())
                .build();
    }
}
