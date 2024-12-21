package com.xavierbouclet.adventofcode;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerResponse;

@Component
public class CustomerHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerHandler.class);

    private final MeterRegistry registry;

    private final Counter callCounter;

    public CustomerHandler(MeterRegistry registry) {
        this.registry=registry;
        this.callCounter = Counter.builder("hello.called")
                .description("Number of time the endpoint /hello is called")
                .register(registry);
    }

    public ServerResponse hello() {
        LOGGER.info("I am an info log");
        LOGGER.debug("I am a debug log");
        LOGGER.error("I am an error log");
        callCounter.increment();
        return ServerResponse.ok().body("Hello from GraalVM");
    }

}
