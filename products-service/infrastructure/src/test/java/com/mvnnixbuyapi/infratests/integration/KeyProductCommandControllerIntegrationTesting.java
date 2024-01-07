package com.mvnnixbuyapi.infratests.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvnnixbuyapi.keyProduct.model.dto.KeyToCreateDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class KeyProductCommandControllerIntegrationTesting {
    @LocalServerPort
    private Integer port;

    @Autowired
    ObjectMapper objectMapper;

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
    @BeforeEach
    void setUp() {
        // Definir la URL base para las solicitudes
        RestAssured.baseURI = "http://localhost:" + this.port;
    }

    @Test
    @DisplayName("Should Create Key - V1")
    public void shouldCreateKeyEndpointRestAssuredV1() {
        // Crear un objeto de ejemplo para enviar en la solicitud
        KeyToCreateDto keyToCreate = new KeyToCreateDto();
        keyToCreate.setKeyCode("exampleKey");
        keyToCreate.setStatus("active");
        keyToCreate.setProductId(123L);

        // Realizar la solicitud POST a /api/create-key-v1
        given()
                .contentType(ContentType.JSON)
                .body(keyToCreate)
                .when()
                .post("/api/create-key-v1")
                .then()
                .statusCode(200) // Reemplaza con el código de estado esperado
                .body("code", hasItems("SUCCESSFUL"))
                .body("data.id", notNullValue()) // Verificar si existe el campo 'id' en la respuesta
                .body("data.keyCode", equalTo("exampleKey"))
                .body("data.status", equalTo("active"))
                .body("data.productId", equalTo(123))
        ;
    }

}
