package com.xavierbouclet.adventofcode;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidCustomer() {
        // Given
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setFirstName("John");
        customer.setLastName("Doe");

        // When
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void testMissingId() {
        // Given
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");

        // When
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        // Then
        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessage()).isEqualTo("The id is mandatory.");
    }

    @Test
    void testBlankFirstName() {
        // Given
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setFirstName("");
        customer.setLastName("Doe");

        // When
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        // Then
        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessage()).isEqualTo("The firstname is mandatory.");
    }

    @Test
    void testFirstNameExceedsMaxLength() {
        // Given
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setFirstName("A".repeat(256)); // 256 characters
        customer.setLastName("Doe");

        // When
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        // Then
        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessage()).isEqualTo("The firstname cannot exceed 255 characters.");
    }
}
