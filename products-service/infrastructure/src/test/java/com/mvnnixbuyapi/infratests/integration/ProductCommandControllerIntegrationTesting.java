package com.mvnnixbuyapi.infratests.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvnnixbuyapi.product.model.dto.command.ProductCreateCommand;
import com.mvnnixbuyapi.product.port.repository.ProductRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ProductCommandControllerIntegrationTesting {
    @LocalServerPort
    private Integer port;

    @Autowired
    ObjectMapper objectMapper;
    private final ProductRepository productRepository;

    @Autowired
    public ProductCommandControllerIntegrationTesting(ProductRepository productRepository){
        this.productRepository = productRepository;
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
    @BeforeEach
    void setUp() {
        // Definir la URL base para las solicitudes
        RestAssured.baseURI = "http://localhost:" + this.port;
    }

    @Test
    @DisplayName("Should Create Product - V1")
    public void shouldCreateProductEndpointRestAssuredV1() {

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
                .body("code", hasItems("SUCCESSFUL"))
                .body("data.id", notNullValue()) // Verificar si existe el campo 'id' en la respuesta
                .body("data.name", equalTo("Prueba 2"))
                .body("data.description", equalTo("Descripcion Prueba 2"));
    }

    @Test
    @DisplayName("Should not create Product with an empty name - V1")
    public void shouldNotCreateProductWithAnEmptyNameV1() {

        // Crear un objeto de ejemplo para enviar en la solicitud
        ProductCreateCommand productCreateCommand = new ProductCreateCommand(
                "",
                "Descripcion Prueba 2"
        );

        // Realizar la solicitud POST a /api/command-product-endpoint/v1/create-product
        given()
                .contentType(ContentType.JSON)
                .body(productCreateCommand)
                .when()
                .post("/api/command-product-endpoint/v1/create-product")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("code", hasItems("EMPTY_NAME_PRODUCT_ERROR"))
        ;
    }

    @Test
    @DisplayName("Should not create Product with an empty description - V1")
    public void shouldNotCreateProductWithAnEmptyDescriptionV1() {

        // Crear un objeto de ejemplo para enviar en la solicitud
        ProductCreateCommand productCreateCommand = new ProductCreateCommand(
                "Prueba 2",
                ""
        );

        // Realizar la solicitud POST a /api/command-product-endpoint/v1/create-product
        given()
                .contentType(ContentType.JSON)
                .body(productCreateCommand)
                .when()
                .post("/api/command-product-endpoint/v1/create-product")
                .then()
                .statusCode(400)
                .body("code", hasItems("EMPTY_DESCRIPTION_PRODUCT_ERROR"))
        ;
    }

    @Test
    @DisplayName("Should not create Product with an empty description and name - V1")
    public void shouldNotCreateProductWithAnEmptyDescriptionAndEmptyNameV1() {

        // Crear un objeto de ejemplo para enviar en la solicitud
        ProductCreateCommand productCreateCommand = new ProductCreateCommand(
                "",
                ""
        );

        // Realizar la solicitud POST a /api/command-product-endpoint/v1/create-product
        given()
                .contentType(ContentType.JSON)
                .body(productCreateCommand)
                .when()
                .post("/api/command-product-endpoint/v1/create-product")
                .then()
                .statusCode(400)
                .body("code", hasItems("EMPTY_NAME_PRODUCT_ERROR","EMPTY_DESCRIPTION_PRODUCT_ERROR"))
        ;
    }

    File getFile(String fileName) throws IOException {
        return new ClassPathResource(fileName).getFile();
    }

    String getFileContent(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(getFile(fileName).getPath())));
    }

    @Test
    @DisplayName("Should Edit Product when photo is being updated - V1")
    public void shouldEditProductEndpointWhenPhotoIsBeingUpdatedRestAssuredV1() throws Exception {
        // Supongamos que tienes los datos necesarios para la edición del producto
        int productId = 1;
        String productName = "Nuevo Nombre";
        String productDescription = "Nueva descripcion";
        String urlImage = "http://ejemplo.com/imagen.jpg";
        String isPhotoUploaded = "true";

        // Realizar la solicitud PATCH a /v1/update-main-photo/{productId}
        given()
                .contentType("multipart/form-data")
                .multiPart("mainPhotoOfProductFile", getFile("testImages/testFileProduct1.png")) // Adjuntar el archivo de imagen como MultipartFile
                .multiPart("productName", productName)
                .multiPart("productDescription", productDescription)
                .multiPart("urlImage", urlImage)
                .multiPart("isPhotoUploaded", isPhotoUploaded)
                .when()
                .patch("/api/command-product-endpoint/v1/update-main-photo/{productId}", productId)
                .then()
                .statusCode(200)
                .body("code", hasItems("SUCCESSFUL"))
                .body("data.id", equalTo(productId)) // Verificar si existe el campo 'id' en la respuesta
                .body("data.name", equalTo(productName))
                .body("data.description", equalTo(productDescription))
                .body("data.urlImage", not(urlImage))

                ;
    }

    @Test
    @DisplayName("Should Edit Product when photo is not being updated - V1")
    public void shouldEditProductEndpointWhenPhotoIsNotBeingUpdatedRestAssuredV1() throws Exception {
        // Supongamos que tienes los datos necesarios para la edición del producto
        int productId = 1;
        String productName = "Nuevo Nombre";
        String productDescription = "Nueva descripcion";
        String urlImage = "https://ejemplo.com/imagen.jpg";
        String isPhotoUploaded = "false";

        // Realizar la solicitud PATCH a /v1/update-main-photo/{productId}
        given()
                .contentType("multipart/form-data")
                .multiPart("mainPhotoOfProductFile", getFile("testImages/testFileProduct1.png")) // Adjuntar el archivo de imagen como MultipartFile
                .multiPart("productName", productName)
                .multiPart("productDescription", productDescription)
                .multiPart("urlImage", urlImage)
                .multiPart("isPhotoUploaded", isPhotoUploaded)
                .when()
                .patch("/api/command-product-endpoint/v1/update-main-photo/{productId}", productId)
                .then()
                .statusCode(200)
                .body("code", hasItems("SUCCESSFUL"))
                .body("data.id", equalTo(productId)) // Verificar si existe el campo 'id' en la respuesta
                .body("data.name", equalTo(productName))
                .body("data.description", equalTo(productDescription))
                .body("data.urlImage", equalTo(urlImage))

        ;
    }

}
