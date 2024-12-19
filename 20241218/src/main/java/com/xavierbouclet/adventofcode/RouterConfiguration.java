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
                .path("/api/customers", builder -> builder
                        .GET(request -> customerHandler.hello())
                )
                .filter(new GlobalErrorHandler())
                .build();
    }
}
