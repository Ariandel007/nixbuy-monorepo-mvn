package com.mvnnixbuyapi.infratests.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvnnixbuyapi.product.command.ProductCreateHandler;
import com.mvnnixbuyapi.product.model.dto.command.ProductCreateCommand;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ProductCommandControllerIntegrationTesting {
    @LocalServerPort
    private Integer port;

    @Autowired
    ObjectMapper objectMapper;
    private final ProductCreateHandler productCreateHandler;

    @Autowired
    public ProductCommandControllerIntegrationTesting(ProductCreateHandler productCreateHandler){
        this.productCreateHandler = productCreateHandler;
    }

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13.9")
            .withDatabaseName("integration-tests-db").withUsername("username").withPassword("password")
            .withInitScript("test-data-1.sql");

    static {
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    public void shouldCreateProductEndpointRestAssured() {
        // Definir la URL base para las solicitudes
        RestAssured.baseURI = "http://localhost:" + this.port; // Reemplaza con la URL de tu servidor

        // Crear un objeto de ejemplo para enviar en la solicitud
        ProductCreateCommand productCreateCommand = new ProductCreateCommand(
                "Prueba 2",
                "Descripcion Prueba 2"
        );

        // Realizar la solicitud POST a /api/command-product-endpoint/v1/create-product
        given()
                .contentType(ContentType.JSON)
                .body(productCreateCommand)
                .when()
                .post("/api/command-product-endpoint/v1/create-product")
                .then()
                .statusCode(200)
                .body("code", equalTo("SUCCESSFUL"))
                .body("message", equalTo("SUCCESSFUL"))
                .body("data.id", notNullValue()) // Verificar si existe el campo 'id' en la respuesta
                .body("data.name", equalTo("Prueba 2"))
                .body("data.description", equalTo("Descripcion Prueba 2"));
    }

}
