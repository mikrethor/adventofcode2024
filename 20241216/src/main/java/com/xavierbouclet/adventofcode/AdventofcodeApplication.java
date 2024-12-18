package com.xavierbouclet.adventofcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.net.URI;

@SpringBootApplication
@EnableConfigurationProperties(SomeConfiguration.class)
public class AdventofcodeApplication {

    private static final Logger logger = LoggerFactory.getLogger(AdventofcodeApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AdventofcodeApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(SomeConfiguration configuration) {
        return args -> {
            logger.info("The url is {}", configuration.url());
            logger.info("The activation state is {}", configuration.activated());
            logger.info("The timeout is {} seconds", configuration.timeout().toSeconds());

        };
    }


}
