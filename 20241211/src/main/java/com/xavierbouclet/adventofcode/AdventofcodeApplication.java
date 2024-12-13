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
                new Customer(null, "Isabelle", "Morel"),
                new Customer(null, "Louis", "Bernard"),
                new Customer(null, "Sophie", "Garcia"),
                new Customer(null, "Marc", "Petit"),
                new Customer(null, "Emilie", "Dubois")
        ));
	}
}
