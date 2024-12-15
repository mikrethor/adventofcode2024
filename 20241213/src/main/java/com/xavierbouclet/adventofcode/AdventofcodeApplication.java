package com.xavierbouclet.adventofcode;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class AdventofcodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdventofcodeApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner( CustomerRepository repository) {
		return args -> repository.saveAll(List.of(
                new Customer(UUID.randomUUID(), "Isabelle", "Morel"),
                new Customer(UUID.randomUUID(), "Louis", "Bernard"),
                new Customer(UUID.randomUUID(), "Sophie", "Garcia"),
                new Customer(UUID.randomUUID(), "Marc", "Petit"),
                new Customer(UUID.randomUUID(), "Emilie", "Dubois")
        ));
	}
}
