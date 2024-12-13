package com.xavierbouclet.adventofcode;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.function.ServerRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RouterConfiguration.class)
@Import(CustomerHandler.class)
class CustomerEndpointMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository customerRepository;


    @Test
    void getCustomers() throws Exception {
        UUID id = UUID.randomUUID();
        String firstName = "Santa";
        String lastName = "Claus";

        when(customerRepository.findAll()).thenReturn(List.of(new Customer(id, firstName, lastName)));

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(id.toString()))
                .andExpect(jsonPath("$[0].firstName").value("Santa"))
                .andExpect(jsonPath("$[0].lastName").value("Claus"));

    }

    @Test
    void getCustomer() throws Exception {
        UUID id = UUID.randomUUID();
        String firstName = "Santa";
        String lastName = "Claus";

        when(customerRepository.findCustomerById(id)).thenReturn(Optional.of(new CustomerRecord(id, firstName, lastName)));

        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("id")).thenReturn(id.toString());

        mockMvc.perform(get("/api/customers/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.firstName").value("Santa"))
                .andExpect(jsonPath("$.lastName").value("Claus"));
    }

    @Test
    void createCustomer() throws Exception {
        UUID firstId = UUID.randomUUID();
        UUID secondId = UUID.randomUUID();
        String firstName = "Santa";
        String lastName = "Claus";

        Customer firstCustomer = new Customer(firstId, firstName, lastName);
        Customer secondCustomer = new Customer(secondId, firstName, lastName);
        when(customerRepository.save(any(Customer.class))).thenReturn(secondCustomer);

        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.body(Customer.class)).thenReturn(firstCustomer);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"id":"%s","firstName":"Santa","lastName":"Claus"}""".formatted(firstId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(secondId.toString()))
                .andExpect(jsonPath("$.firstName").value("Santa"))
                .andExpect(jsonPath("$.lastName").value("Claus"));
    }

    @Test
    void deleteCustomer() throws Exception {
        UUID id = UUID.randomUUID();

        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("id")).thenReturn(id.toString());

        mockMvc.perform(delete("/api/customers/" + id))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }
}