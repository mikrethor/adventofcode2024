package com.xavierbouclet.adventofcode;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.time.Instant;

@SpringBootApplication
public class AdventofcodeApplication {


    public static void main(String[] args) {
        SpringApplication.run(AdventofcodeApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ProductService service) {
        return args -> {
            var before = Instant.now();
            var first = service.getProductById(1);
            var middle = Instant.now();
            var second = service.getProductById(1);
            var end = Instant.now();

            Duration withCache = Duration.between(middle, end);
            Duration withoutCache = Duration.between(before, middle);

            System.out.println("With cache: "+withCache.toMillis()+ " ms");
            System.out.println("Without cache: "+withoutCache.toMillis()+ " ms");

        };

    }

}
