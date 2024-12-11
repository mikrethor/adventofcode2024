package com.xavierbouclet.adventofcode;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.servlet.function.ServerRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerEndpointRestAssuredTest {

    @LocalServerPort
    private int port;

    @MockitoBean
    private CustomerRepository customerRepository;

    @BeforeEach
    void setup() {

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }


    @Test
    void getCustomers() {
        UUID id = UUID.randomUUID();
        String firstName = "Santa";
        String lastName = "Claus";

        when(customerRepository.findAll()).thenReturn(List.of(new Customer(id, firstName, lastName)));

        given()
                .when()
                .get("/api/customers")
                .then()
                .contentType("application/json")
                .statusCode(200)
                .body("[0].id", equalTo(id.toString()))
                .body("[0].firstName", equalTo(firstName))
                .body("[0].lastName", equalTo(lastName));
    }

    @Test
    void getCustomer() {
        UUID id = UUID.randomUUID();
        String firstName = "Santa";
        String lastName = "Claus";

        when(customerRepository.findCustomerById(id)).thenReturn(Optional.of(new CustomerRecord(id, firstName, lastName)));

        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("id")).thenReturn(id.toString());

        given()
                .pathParam("id", id.toString())
                .when()
                .get("/api/customers/{id}")
                .then()
                .contentType("application/json")
                .statusCode(200) // Verify HTTP 204 No Content
                .body("id", equalTo(id.toString()))
                .body("firstName", equalTo(firstName))
                .body("lastName", equalTo(lastName));
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

        given()
                .contentType("application/json")
                .when()
                .body("""
                        {"id":"%s","firstName":"Santa","lastName":"Claus"}""".formatted(firstId))
                .post("/api/customers")
                .then()
                .contentType("application/json")
                .statusCode(200)
                .body("id", equalTo(secondId.toString()))
                .body("firstName", equalTo(firstName))
                .body("lastName", equalTo(lastName));
    }

    @Test
    void deleteCustomer() throws Exception {
        UUID id = UUID.randomUUID();

        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("id")).thenReturn(id.toString());

        given()
                .pathParam("id", id)
                .when()
                .delete("/api/customers/{id}")
                .then()
                .statusCode(204)
                .body(emptyOrNullString());
    }
}