package com.xavierbouclet.adventofcode;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerResponse;

@Component
public class CustomerHandler {

    private final MeterRegistry registry;

    private final Counter callCounter;

    public CustomerHandler(MeterRegistry registry) {
        this.registry = registry;
        this.callCounter = Counter.builder("hello.called")
                .description("Tells how many times the endpoints has beend called since startup")
                .register(registry);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerHandler.class);

    public ServerResponse hello() {
        LOGGER.info("This is an info log");
        LOGGER.debug("This is a debug log");
        LOGGER.error("This is an error log");

        callCounter.increment();

        return ServerResponse.ok().body("Hello from GraalVM");
    }

}
