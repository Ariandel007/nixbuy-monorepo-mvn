package com.mvnnixbuyapi.userservice.integrationtesting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvnnixbuyapi.userservice.data.DataToTest;
import com.mvnnixbuyapi.userservice.dto.LoginUserDto;
import com.mvnnixbuyapi.userservice.dto.UserRegisterDto;
import com.mvnnixbuyapi.userservice.dto.UserToUpdateDto;
import com.mvnnixbuyapi.userservice.utils.UserServiceMessageErrors;
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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;


import static org.hamcrest.Matchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class UserControllerIntegrationTests {
    @LocalServerPort
    private Integer port;

    @Autowired
    ObjectMapper objectMapper;

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13.9")
            .withDatabaseName("integration-tests-db").withUsername("username").withPassword("password")
            .withInitScript("test-data.sql");

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
    @DisplayName("Should register a new user")
    void shouldRegisterUserV1() throws Exception {
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setUsername("nuevo_usuario");
        userRegisterDto.setPassword("Password123!");
        userRegisterDto.setEmail("nuevo@correo.com");
        userRegisterDto.setFirstname("Nuevo");
        userRegisterDto.setLastname("Usuario");
        userRegisterDto.setCountry("Ejemplolandia");
        userRegisterDto.setCity("Ciudad Ejemplo");
        userRegisterDto.setBirthDateUtc("2000-01-01T12:00:00.000Z");

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(userRegisterDto))
                .when()
                .post("/api/users/v1/register-user")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("username", equalTo("nuevo_usuario"))
                .body("email", equalTo("nuevo@correo.com"))
                .body("firstname", equalTo("Nuevo"))
                .body("lastname", equalTo("Usuario"))
                .body("country", equalTo("Ejemplolandia"))
                .body("city", equalTo("Ciudad Ejemplo"));
    }


    @Test
    @DisplayName("Should Find User Basic Info")
    void shouldFindUserBasicInfoByIdV1() {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/users/v1/basic-user-info/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("username", equalTo("ejemplo_usuario1"))
                .body("country", equalTo("Ejemplolandia"))
                .body("city", equalTo("Ciudad Ejemplo"));
    }

    @Test
    @DisplayName("Should update user information")
    void shouldUpdateUserV1() throws Exception {
        UserToUpdateDto userToUpdateDto = new UserToUpdateDto();
        userToUpdateDto.setFirstname("NombreActualizado");
        userToUpdateDto.setLastname("ApellidoActualizado");
        userToUpdateDto.setCountry("NuevoPais");
        userToUpdateDto.setCity("NuevaCiudad");
        userToUpdateDto.setBirthDate(Instant.parse("2000-01-01T00:00:00Z"));
        userToUpdateDto.setAccountCreationDate(Instant.parse("2023-08-03T23:40:37.934Z"));

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(userToUpdateDto))
                .when()
                .patch("/api/users/v1/update-user-info/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("firstname", equalTo("NombreActualizado"))
                .body("lastname", equalTo("ApellidoActualizado"))
                .body("country", equalTo("NuevoPais"))
                .body("city", equalTo("NuevaCiudad"));
    }


    @Test
    @DisplayName("Should update user password")
    void shouldUpdateUserPasswordV1() throws Exception {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(DataToTest.passwordToUpdateDto()))
                .when()
                .patch("/api/users/v1/update-user-password/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("username", equalTo("ejemplo_usuario1"));
    }

    @Test
    @DisplayName("Should not found user to update passsword")
    void shoulNotFoundUserToUpdatePasswordV1() throws Exception {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(DataToTest.passwordToUpdateDto()))
                .when()
                .patch("/api/users/v1/update-user-password/9999999")
                .then()
                .statusCode(404)
                .contentType(ContentType.JSON)
                .body("errorCode", equalTo(UserServiceMessageErrors.USER_TO_UPDATE_NOT_FOUND))
                .body("message", equalTo(UserServiceMessageErrors.USER_TO_UPDATE_NOT_FOUND_MSG));
    }

    @Test
    @DisplayName("Should Fail When Updating and empty password")
    void shouldFailWhenUpdatingPasswordEmptyPasswordV1() throws Exception {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(DataToTest.emptyPasswordToUpdateDto()))
                .when()
                .patch("/api/users/v1/update-user-password/1")
                .then()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .body("errorCode", equalTo(UserServiceMessageErrors.INVALID_PATTERN_OF_PASSWORD));
    }

    @Test
    @DisplayName("Should update photo Url for User")
    void shouldUpdatePhotoUrlForUser() throws Exception {
        Path path = Paths.get("src/test/resources/testImages/testFile1.jpg");
        byte[] fileContent = Files.readAllBytes(path);

        RestAssured
                .given()
                .contentType(ContentType.MULTIPART)
                .multiPart("userPhotoFile", "test.jpg", fileContent)
                .accept(ContentType.JSON)
                .when()
                .patch("/api/users/v1/update-photo-url/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("username", equalTo("ejemplo_usuario1"))
                .body("email", equalTo("ejemplo@correo.com"))
                .body("country", equalTo("Ejemplolandia"))
                .body("city", equalTo("Ciudad Ejemplo"))
                .body("photoUrl", not(emptyString()));
    }

    @Test
    @DisplayName("Should find list of users")
    void shouldFindUsersListV1() {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/users/v1/find-users-list/0")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0))
                .body("[0].id", notNullValue())
                .body("[0].username", not(emptyString()))
                .body("[0].blocked", notNullValue())
                .body("[0].roles", not(emptyString()));
    }

    @Test
    @DisplayName("Should generate authentication token")
    void shouldGenerateToken() throws Exception {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(DataToTest.passwordToUpdateDto()))
                .when()
                .patch("/api/users/v1/update-user-password/5")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(5))
                .body("username", equalTo("ejemplo_usuario5"));


        // Crea un objeto LoginUserDto con las credenciales del usuario
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setUsername("ejemplo_usuario5");
        loginUserDto.setPassword("PruebaPassNueva1234@"); // Asegúrate de que coincida con la contraseña hasheada en la base de datos para el usuario

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(loginUserDto))
                .when()
                .post("/api/security/v1/authenticate")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("token", not(emptyString())); // Verifica que el token no esté vacío
    }


}