package com.xavierbouclet.adventofcode;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RouterConfiguration.class)
@Import(CustomerHandler.class)
class CustomerEndpointMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
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
                .andExpect(jsonPath("$[0].firstName").value(firstName))
                .andExpect(jsonPath("$[0].lastName").value(lastName));
    }

    @Test
    void getCustomer() throws Exception {
        UUID id = UUID.randomUUID();
        String firstName = "Santa";
        String lastName = "Claus";

        when(customerRepository.findById(id)).thenReturn(Optional.of(new Customer(id, firstName, lastName)));

        mockMvc.perform(get("/api/customers/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.firstName").value(firstName))
                .andExpect(jsonPath("$.lastName").value(lastName));
    }

    @Test
    void createCustomer() throws Exception {
        UUID firstId = UUID.randomUUID();
        UUID secondId = UUID.randomUUID();
        String firstName = "Santa";
        String lastName = "Claus";

        Customer firstCustomer = new Customer(firstId, firstName, lastName);
        Customer secondCustomer = new Customer(secondId, firstName, lastName);

        when(customerRepository.save(firstCustomer)).thenReturn(secondCustomer);

        mockMvc.perform(post("/api/customers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"id":"%s","firstName":"%s","lastName":"%s"}""".formatted(firstId, firstName, lastName)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(secondId.toString()))
                .andExpect(jsonPath("$.firstName").value(firstName))
                .andExpect(jsonPath("$.lastName").value(lastName));
    }

    @Test
    void deleteCustomer() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/customers/" + id))
                .andExpect(status().isNoContent());
    }
}