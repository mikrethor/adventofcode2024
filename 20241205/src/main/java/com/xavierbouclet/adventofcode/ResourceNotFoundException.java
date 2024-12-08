package com.xavierbouclet.adventofcode;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException() {
        super("Not Found");
    }
}
