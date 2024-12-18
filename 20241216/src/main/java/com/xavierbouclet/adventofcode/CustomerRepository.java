package com.xavierbouclet.adventofcode;

import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface CustomerRepository extends ListCrudRepository<Customer, UUID> {
}
