package com.xavierbouclet.adventofcode;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MaxDurationValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxDuration {
    String message() default "The duration should be less than or equal to {max}";
    long max(); // valeur maximale en secondes
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

