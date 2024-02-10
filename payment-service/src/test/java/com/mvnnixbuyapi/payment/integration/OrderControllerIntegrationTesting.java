package com.mvnnixbuyapi.payment.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvnnixbuyapi.paymentservice.dto.request.CreateOrderDto;
import com.mvnnixbuyapi.paymentservice.dto.request.ItemCartDto;
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

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class OrderControllerIntegrationTesting {

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
    @DisplayName("Should Create Product - V1")
    public void shouldCreateProductEndpointRestAssuredV1() {

        // Crear un objeto de ejemplo para enviar en la solicitud
        CreateOrderDto createOrderDto = new CreateOrderDto();
        createOrderDto.setItemCartDtoList(List.of(new ItemCartDto(1L, 1)));
//        Long idUser = 1L;

        // Realizar la solicitud POST a /api/command-product-endpoint/v1/create-product
        given()
                .contentType(ContentType.JSON)
                .body(createOrderDto)
                .when()
                .post("/api/payment/v1/create-order/1")
                .then()
                .statusCode(200)
                .body("code", hasItems("SUCCESSFUL"))
        ;
    }

}
