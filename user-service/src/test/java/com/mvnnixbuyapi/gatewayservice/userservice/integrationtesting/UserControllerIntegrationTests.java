package com.mvnnixbuyapi.gatewayservice.userservice.integrationtesting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvnnixbuyapi.gatewayservice.userservice.services.UserApplicationService;
import com.mvnnixbuyapi.gatewayservice.userservice.data.DataToTest;
import com.mvnnixbuyapi.gatewayservice.userservice.utils.UserServiceMessageErrors;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class UserControllerIntegrationTests {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserApplicationService userService;

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

    @Test
    void shouldFindUserBasicInfoByIdV1() throws Exception {

        mvc.perform(get("/api/users/v1/basic-user-info/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("ejemplo_usuario1"))
                .andExpect(jsonPath("$.country").value("Ejemplolandia"))
                .andExpect(jsonPath("$.city").value("Ciudad Ejemplo"))
//                .andExpect(jsonPath("$.birthDate").value(userSaved.getBirthDate()))
//                .andExpect(jsonPath("$.accountCreationDate").value(userSaved.getAccountCreationDate()))
                .andExpect(jsonPath("$.photoUrl").value(IsNull.nullValue()));

    }

    @Test
    void shouldUpdateUserPasswordV1() throws Exception {
        // Given
        mvc.perform(patch("/api/users/v1/update-user-password/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(DataToTest.passwordToUpdateDto()))
                )
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("ejemplo_usuario1"))
                ;
    }

    @Test
    void shoulNotFoundUserToUpdatePasswordV1() throws Exception {
        // Given
        mvc.perform(patch("/api/users/v1/update-user-password/9999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(DataToTest.passwordToUpdateDto()))
                )
                // Then
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(UserServiceMessageErrors.USER_TO_UPDATE_NOT_FOUND))
                .andExpect(jsonPath("$.message").value(UserServiceMessageErrors.USER_TO_UPDATE_NOT_FOUND_MSG))
        ;
    }

    @Test
    void shouldFailWhenUpdatingPasswordEmptyPasswordV1() throws Exception {
        mvc.perform(patch("/api/users/v1/update-user-password/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(DataToTest.emptyPasswordToUpdateDto()))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(UserServiceMessageErrors.INVALID_PATTERN_OF_PASSWORD))
        ;
    }

    @Test
    void shouldFailWhenUpdatingPasswordWithoutUpperCasesV1() throws Exception {
        mvc.perform(patch("/api/users/v1/update-user-password/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(DataToTest.passwordWithoutUpperCasesToUpdateDto()))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(UserServiceMessageErrors.INVALID_PATTERN_OF_PASSWORD))
        ;

    }

    @Test
    void shouldFailWhenUpdatingPasswordWithoutLowerCasesV1() throws Exception {
        mvc.perform(patch("/api/users/v1/update-user-password/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(DataToTest.passwordWithoutLowerCasesToUpdateDto()))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(UserServiceMessageErrors.INVALID_PATTERN_OF_PASSWORD))
        ;

    }

    @Test
    void shouldFailWhenUpdatingPasswordWithoutNumberV1() throws Exception {
        mvc.perform(patch("/api/users/v1/update-user-password/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(DataToTest.passwordWithoutNumberToUpdateDto()))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(UserServiceMessageErrors.INVALID_PATTERN_OF_PASSWORD))
        ;

    }

    @Test
    void shouldFailWhenUpdatingPasswordWithoutSpecialCharactersV1() throws Exception {
        mvc.perform(patch("/api/users/v1/update-user-password/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(DataToTest.passwordWithoutSpecialCharactersToUpdateDto()))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(UserServiceMessageErrors.INVALID_PATTERN_OF_PASSWORD))
        ;

    }

    @Test
    void shouldFailWhenUpdatingPasswordWithLessThanTwelveCharactersV1() throws Exception {
        mvc.perform(patch("/api/users/v1/update-user-password/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(DataToTest.passwordWithLessThanTwelveCharactersToUpdateDto()))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(UserServiceMessageErrors.INVALID_PATTERN_OF_PASSWORD))
        ;

    }


}