package com.xavierbouclet.adventofcode;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.validation.annotation.Validated;

import java.net.URI;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@ConfigurationProperties(prefix = "my.properties")
@Validated
public record SomeConfiguration(@NotNull URI url,
                                boolean activated,
                                @MaxDuration(max = 3600, message = "The duration should be less than or equal to (3600 secondes)")
                                @DurationUnit(ChronoUnit.SECONDS)
                                Duration timeout) {
}
