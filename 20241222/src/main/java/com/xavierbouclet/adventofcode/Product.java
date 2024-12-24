package com.xavierbouclet.adventofcode;

public record Product(
        int id,
        String title,
        double price,
        String description,
        String category,
        String image,
        Rating rating
) {
    private record Rating(
            double rate,
            int count
    ) {}
}

