package com.xavierbouclet.adventofcode;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends ListCrudRepository<Customer, UUID> {

    @Query("SELECT new com.xavierbouclet.adventofcode.CustomerRecord(c.id, c.firstName, c.lastName) FROM Customer c WHERE c.id = :id")
    Optional<CustomerRecord> findCustomerById(@Param("id") UUID id);
}
