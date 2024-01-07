package com.mvnnixbuyapi.infratests.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvnnixbuyapi.keyProduct.model.dto.KeyToCreateDto;
import com.mvnnixbuyapi.keyProduct.model.dto.command.KeyCreateCommand;
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
    public void shouldCreateKeyEndpointV1() {
        // Crear un objeto de ejemplo para enviar en la solicitud
        KeyCreateCommand keyToCreate = new KeyCreateCommand();
        keyToCreate.setKeyCode("exampleKey");
        keyToCreate.setStatus("active");
        keyToCreate.setIdProduct(1L);
        keyToCreate.setIdPlatform(1L);

        // Realizar la solicitud POST a /api/create-key-v1
        given()
                .contentType(ContentType.JSON)
                .body(keyToCreate)
                .when()
                .post("/api/command-key-endpoint/v1/create-key")
                .then()
                .statusCode(200) // Reemplaza con el código de estado esperado
                .body("code", hasItems("SUCCESSFUL"))
                .body("data.id", notNullValue()) // Verificar si existe el campo 'id' en la respuesta
                .body("data.keyCode", equalTo("exampleKey"))
                .body("data.status", equalTo("active"))
                .body("data.productId", equalTo(1))
        ;
    }

    @Test
    @DisplayName("Should not create Key when code is null - V1")
    public void shouldNotCreateKeyEndpointWhenKeyCodeIsNullV1() {
        // Crear un objeto de ejemplo para enviar en la solicitud
        KeyCreateCommand keyToCreate = new KeyCreateCommand();
        keyToCreate.setKeyCode(null);
        keyToCreate.setStatus("active");
        keyToCreate.setIdProduct(1L);
        keyToCreate.setIdPlatform(1L);

        // Realizar la solicitud POST a /api/create-key-v1
        given()
                .contentType(ContentType.JSON)
                .body(keyToCreate)
                .when()
                .post("/api/command-key-endpoint/v1/create-key")
                .then()
                .statusCode(400) // Reemplaza con el código de estado esperado
                .body("code", hasItems("EMPTY_KEY_CODE_ERROR"))
        ;

    }

    @Test
    @DisplayName("Should not create Key when status is null - V1")
    public void shouldNotCreateKeyEndpointWhenStatusIsNullV1() {
        // Crear un objeto de ejemplo para enviar en la solicitud
        KeyCreateCommand keyToCreate = new KeyCreateCommand();
        keyToCreate.setKeyCode("exampleKey2");
        keyToCreate.setStatus(null);
        keyToCreate.setIdProduct(1L);
        keyToCreate.setIdPlatform(1L);

        // Realizar la solicitud POST a /api/create-key-v1
        given()
                .contentType(ContentType.JSON)
                .body(keyToCreate)
                .when()
                .post("/api/command-key-endpoint/v1/create-key")
                .then()
                .statusCode(400) // Reemplaza con el código de estado esperado
                .body("code", hasItems("EMPTY_KEY_STATUS_ERROR"))
        ;

    }

    @Test
    @DisplayName("Should not create Key when idProduct is null - V1")
    public void shouldNotCreateKeyEndpointWhenIdProductIsNullV1() {
        // Crear un objeto de ejemplo para enviar en la solicitud
        KeyCreateCommand keyToCreate = new KeyCreateCommand();
        keyToCreate.setKeyCode("exampleKey2");
        keyToCreate.setStatus("active");
        keyToCreate.setIdProduct(null);
        keyToCreate.setIdPlatform(1L);

        // Realizar la solicitud POST a /api/create-key-v1
        given()
                .contentType(ContentType.JSON)
                .body(keyToCreate)
                .when()
                .post("/api/command-key-endpoint/v1/create-key")
                .then()
                .statusCode(400) // Reemplaza con el código de estado esperado
                .body("code", hasItems("NULL_ID_PRODUCT_ERROR"))
        ;

    }

    @Test
    @DisplayName("Should not create Key when idPlatform is null - V1")
    public void shouldNotCreateKeyEndpointWhenIdPlatformIsNullV1() {
        // Crear un objeto de ejemplo para enviar en la solicitud
        KeyCreateCommand keyToCreate = new KeyCreateCommand();
        keyToCreate.setKeyCode("exampleKey2");
        keyToCreate.setStatus("active");
        keyToCreate.setIdProduct(1L);
        keyToCreate.setIdPlatform(null);

        // Realizar la solicitud POST a /api/create-key-v1
        given()
                .contentType(ContentType.JSON)
                .body(keyToCreate)
                .when()
                .post("/api/command-key-endpoint/v1/create-key")
                .then()
                .statusCode(400) // Reemplaza con el código de estado esperado
                .body("code", hasItems("NULL_ID_PLATFORM_ERROR"))
        ;

    }

}
