package com.mvnnixbuyapi.infratests.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvnnixbuyapi.product.model.dto.query.ItemCartDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ProductQueryControllerIntegrationTesting {
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
    @DisplayName("Should Get Products of Order id - V1")
    public void shouldGetProductsOfOrderEndpointRestAssuredV1() {

        // Datos de prueba
        Long orderId = 1L;

        // Realizar la solicitud GET a /v1/get-products-of-orderId
        given()
                .param("orderId", orderId)
                .when()
                .get("/api/query-product-endpoint/v1/get-products-of-orderId")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("code", hasItems("SUCCESSFUL"))
                .body("data", notNullValue())
                .body("data", hasSize(2))
                .body("data[0].id", notNullValue())
                .body("data[0].name", notNullValue())
                .body("data[0].description", notNullValue())
                .body("data[0].price", notNullValue())
        ;
    }

    @Test
    @DisplayName("Should Get Products To Add to Order - V1")
    public void shouldGetProductsToAddToOrderEndpointRestAssuredV1() {

        // Datos de prueba
        List<ItemCartDto> itemCartDtoList = new ArrayList<>();
        itemCartDtoList.add(new ItemCartDto(3L,1));

        // Realizar la solicitud GET a /v1/get-products-of-orderId
        given()
                .contentType(ContentType.JSON)
                .body(itemCartDtoList)
                .when()
                .post("/api/query-product-endpoint/v1/products-to-add-to-order")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("code", hasItems("SUCCESSFUL"))
                .body("data", notNullValue())
                .body("data", hasSize(1))
                .body("data[0].id", notNullValue())
                .body("data[0].name", notNullValue())
                .body("data[0].description", notNullValue())
                .body("data[0].price", notNullValue())
        ;
    }

}
