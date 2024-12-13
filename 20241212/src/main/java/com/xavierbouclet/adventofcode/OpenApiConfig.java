package com.xavierbouclet.adventofcode;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.RouterOperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("API to handle customers")
                .version("1.0")
                .description("Generated doc for the customer API with Spring Doc"));
    }

    @Bean
    public RouterOperationCustomizer routerOperationCustomizer() {
        return (operation, handlerMethod) -> operation;
    }
}
