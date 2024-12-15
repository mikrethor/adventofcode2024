package com.xavierbouclet.adventofcode;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.text.MatchesPattern.matchesPattern;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration;

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class CustomerEndpointITTest {

    @LocalServerPort
    private int port;

    private RequestSpecification spec;

    @Container
    private static final PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa").waitingFor(Wait.forListeningPort());

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }


    @BeforeEach
    void setup(RestDocumentationContextProvider provider) {
        this.spec = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(provider).operationPreprocessors())
                .build();
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void getCustomers() {
        UUID id = UUID.fromString("86d50633-a49f-4aee-8429-e085eb4d3fc4");
        String firstName = "Santa";
        String lastName = "Claus";

        List<Customer> response = given(this.spec)
                .log().all()
                .filter(document("{method-name}",
                        ResourceSnippetParameters.builder()
                                .tag("tag-customer")
                                .summary("Returns all customers")
                                .privateResource(false)
                                .deprecated(false)
                                .description("Returns all the customers"),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                beneathPath("[]").withSubsectionId("customers"),
                                commonCustomerFields()
                        )
                ))
                .when()
                .get("/api/customers")
                .then()
                .contentType("application/json")
                .statusCode(200)
                .body("size()", equalTo(13))
                .extract()
                .body().jsonPath().getList(".", Customer.class);

        var customer = response.stream().filter(c -> c.getId().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("failed"));

        assertThat(customer).isEqualTo(new Customer(id, firstName, lastName));
    }

    private FieldDescriptor[] commonCustomerFields() {
        return new FieldDescriptor[]{
                fieldWithPath("id").description("Identifier"),
                fieldWithPath("firstName").description("FirstName"),
                fieldWithPath("lastName").description("LastName")
        };

    }

    @Test
    void getCustomer() {
        UUID id = UUID.fromString("86d50633-a49f-4aee-8429-e085eb4d3fc4");
        String firstName = "Santa";
        String lastName = "Claus";

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
        String firstName = "Rudolph";
        String lastName = "Deer";

        given()
                .contentType("application/json")
                .when()
                .body("""
                        {"id":"%s","firstName":"Rudolph","lastName":"Deer"}""".formatted(firstId))
                .post("/api/customers")
                .then()
                .contentType("application/json")
                .statusCode(200)
                .body("id", matchesPattern("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"))
                .body("firstName", equalTo(firstName))
                .body("lastName", equalTo(lastName));
    }

    @Test
    void deleteCustomer() throws Exception {
        UUID id = UUID.randomUUID();

        given()
                .pathParam("id", id)
                .when()
                .delete("/api/customers/{id}")
                .then()
                .statusCode(204)
                .body(emptyOrNullString());
    }
}