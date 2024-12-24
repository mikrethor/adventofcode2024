package com.xavierbouclet.adventofcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerResponse;

@Component
public class CustomerHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerHandler.class);

    public ServerResponse hello() {
        LOGGER.info("This is an info log");
        LOGGER.debug("This is a debug log");
        LOGGER.error("This is an error log");


        return ServerResponse.ok().body("Hello from GraalVM");
    }

}
