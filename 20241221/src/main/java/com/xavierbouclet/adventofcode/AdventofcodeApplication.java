package com.xavierbouclet.adventofcode;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AdventofcodeApplication {


    public static void main(String[] args) {
        SpringApplication.run(AdventofcodeApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ProductService productService) {
        return args -> {

            for (Product product : productService.getProducts()) {
                System.out.println("My product is : %s".formatted(product));
            }

            System.out.println("----------");

            System.out.println(productService.getProductById(1));
        };
    }


}
