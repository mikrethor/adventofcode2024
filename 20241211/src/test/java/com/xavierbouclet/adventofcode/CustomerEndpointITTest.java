package com.xavierbouclet.adventofcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.text.MatchesPattern.matchesPattern;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class CustomerEndpointsITTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:17.2")
            .waitingFor(Wait.forListeningPort());

    @DynamicPropertySource
    private static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @LocalServerPort
    private int port;

    @BeforeEach
    void beforeEach() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void getCustomers() {
        UUID id = UUID.fromString("86d50633-a49f-4aee-8429-e085eb4d3fc4");
        String firstName = "Santa";
        String lastName = "Claus";

        var customers = given()
                .when()
                .get("/api/customers")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract()
                .body().jsonPath().getList(".", Customer.class);

        assertThat(customers).contains(new Customer(id, firstName, lastName));
    }

    @Test
    void getCustomer() {
        UUID id = UUID.fromString("86d50633-a49f-4aee-8429-e085eb4d3fc4");
        String firstName = "Santa";
        String lastName = "Claus";


        given()
                .when()
                .get("/api/customers/" + id)
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", equalTo(id.toString()))
                .body("firstName", equalTo(firstName))
                .body("lastName", equalTo(lastName));
    }

    @Test
    void createCustomer() {
        String firstName = "Santa";
        String lastName = "Claus";

        given()
                .contentType("application/json")
                .when()
                .body("""
                        {"firstName":"%s","lastName":"%s"}""".formatted(firstName, lastName))
                .post("/api/customers")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", matchesPattern("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"))
                .body("firstName", equalTo(firstName))
                .body("lastName", equalTo(lastName));
    }

    @Test
    void deleteCustomer() {
        UUID id = UUID.randomUUID();

        given()
                .when()

                .delete("/api/customers/" + id)
                .then()
                .statusCode(204);
    }

}