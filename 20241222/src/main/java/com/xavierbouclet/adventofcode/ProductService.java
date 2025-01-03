package com.xavierbouclet.adventofcode;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange("/products")
public interface ProductService {

    @GetExchange
    List<Product> getProducts();

    @GetExchange("/{id}")
    Product getProductById(@PathVariable  long id);
}
