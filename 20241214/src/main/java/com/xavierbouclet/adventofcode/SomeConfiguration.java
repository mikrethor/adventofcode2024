package com.xavierbouclet.adventofcode;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

import java.net.URI;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@ConfigurationProperties(prefix = "my.properties")
public record SomeConfiguration(URI url,
                                boolean activated,
                                @DurationUnit(ChronoUnit.SECONDS) Duration timeout) {
}
