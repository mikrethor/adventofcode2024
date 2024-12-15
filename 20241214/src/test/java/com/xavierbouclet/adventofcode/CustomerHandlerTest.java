package com.xavierbouclet.adventofcode;

import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JsonContentAssert;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerHandlerTest {

    private CustomerHandler customerHandler;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void beforeEach() {
        customerHandler = new CustomerHandler(customerRepository);
    }

    @Test
    void getCustomers() throws ServletException, IOException {
        UUID id = UUID.randomUUID();
        String firstName = "Santa";
        String lastName = "Claus";

        when(customerRepository.findAll()).thenReturn(List.of(new Customer(id, firstName, lastName)));

        ServerResponse response = customerHandler.getCustomers();

        String body = ServerResponseExtractor.extractBody(response);

        new JsonContentAssert(this.getClass(), body).isEqualToJson("""
                [{"id":"%s","firstName":"Santa","lastName":"Claus"}]""".formatted(id));

    }

    @Test
    void getCustomer() throws ServletException, IOException {
        UUID id = UUID.randomUUID();
        String firstName = "Santa";
        String lastName = "Claus";

        when(customerRepository.findCustomerById(id)).thenReturn(Optional.of(new CustomerRecord(id, firstName, lastName)));

        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("id")).thenReturn(id.toString());

        ServerResponse response = customerHandler.getCustomer(serverRequest);

        String body = ServerResponseExtractor.extractBody(response);

        new JsonContentAssert(this.getClass(), body).isEqualToJson("""
                {"id":"%s","firstName":"Santa","lastName":"Claus"}""".formatted(id));
    }

    @Test
    void createCustomer() throws ServletException, IOException {
        UUID firstId = UUID.randomUUID();
        UUID secondId = UUID.randomUUID();
        String firstName = "Santa";
        String lastName = "Claus";

        Customer firstCustomer = new Customer(firstId, firstName, lastName);
        Customer secondCustomer = new Customer(secondId, firstName, lastName);
        when(customerRepository.save(any(Customer.class))).thenReturn(secondCustomer);

        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.body(Customer.class)).thenReturn(firstCustomer);

        ServerResponse response = customerHandler.createCustomer(serverRequest);

        String body = ServerResponseExtractor.extractBody(response);

        new JsonContentAssert(this.getClass(), body).isEqualToJson("""
                {"id":"%s","firstName":"Santa","lastName":"Claus"}""".formatted(secondId));
    }

    @Test
    void deleteCustomer() {
        UUID id = UUID.randomUUID();

        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("id")).thenReturn(id.toString());

        ServerResponse response = customerHandler.deleteCustomer(serverRequest);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}