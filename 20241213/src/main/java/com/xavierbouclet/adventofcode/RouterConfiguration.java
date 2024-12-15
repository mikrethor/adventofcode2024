package com.xavierbouclet.adventofcode;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration(proxyBeanMethods = false)
public class RouterConfiguration {

    @Bean
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
