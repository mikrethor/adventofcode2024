package com.xavierbouclet.adventofcode;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("Customer not found !");
    }
}
