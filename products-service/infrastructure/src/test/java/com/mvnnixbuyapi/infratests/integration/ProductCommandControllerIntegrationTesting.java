package com.mvnnixbuyapi.infratests.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvnnixbuyapi.product.command.ProductCreateHandler;
import com.mvnnixbuyapi.product.model.dto.command.ProductCreateCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class ProductCommandControllerIntegrationTesting {
    @Autowired
    private MockMvc mvc;
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

    @Test
    public void testCreateProductEndpoint() throws Exception {
        // Crear un objeto de ejemplo para enviar en la solicitud
        ProductCreateCommand productCreateCommand = new ProductCreateCommand(
                "Prueba 1",
                "Descripcion Prueba 1"
        );

        // Convertir el objeto a formato JSON
        String requestBody = objectMapper.writeValueAsString(productCreateCommand);

        // Simular la solicitud POST a /v1/create-product
        mvc.perform(
                post("/api/command-product-endpoint/v1/create-product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESSFUL"))
                .andExpect(jsonPath("$.message").value("SUCCESSFUL"))
                .andExpect(jsonPath("$.data.id").exists()) // Verificar si existe el campo 'id' en la respuesta
                .andExpect(jsonPath("$.data.name").value("Prueba 1"))
                .andExpect(jsonPath("$.data.description").value("Descripcion Prueba 1"));
    }

}
