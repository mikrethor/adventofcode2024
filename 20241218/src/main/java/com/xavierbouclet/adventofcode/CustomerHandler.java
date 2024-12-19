package com.xavierbouclet.adventofcode;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerResponse;

@Component
public class CustomerHandler {

    public ServerResponse hello() {
        return ServerResponse.ok().body("Hello from GraalVM");
    }

}
