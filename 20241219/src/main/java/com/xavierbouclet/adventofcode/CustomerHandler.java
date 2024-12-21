package com.xavierbouclet.adventofcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerResponse;

@Component
public class CustomerHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerHandler.class);


    public ServerResponse hello() {
        LOGGER.info("I am an info log");
        LOGGER.debug("I am a debug log");
        LOGGER.error("I am an error log");

        return ServerResponse.ok().body("Hello from GraalVM");
    }

}
