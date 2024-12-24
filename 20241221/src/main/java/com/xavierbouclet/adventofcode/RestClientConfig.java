package com.xavierbouclet.adventofcode;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration(proxyBeanMethods = false)
public class RestClientConfig {

    RestClient defaultClient = RestClient.create();

    @Bean
    RestClient productRestClient() {
        return RestClient.builder()
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .baseUrl("https://fakestoreapi.com")
                .build();
    }
}
