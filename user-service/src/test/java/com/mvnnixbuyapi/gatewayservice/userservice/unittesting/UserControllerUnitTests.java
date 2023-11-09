package com.mvnnixbuyapi.gatewayservice.userservice.unittesting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvnnixbuyapi.gatewayservice.userservice.data.DataToTest;
import com.mvnnixbuyapi.gatewayservice.userservice.dto.UserRegisterDto;
import com.mvnnixbuyapi.gatewayservice.userservice.dto.UserToFindDto;
import com.mvnnixbuyapi.gatewayservice.userservice.services.UserApplicationService;
import com.mvnnixbuyapi.gatewayservice.userservice.config.SecurityConfiguration;
import com.mvnnixbuyapi.gatewayservice.userservice.controllers.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {SecurityConfiguration.class, UserController.class})
public class UserControllerUnitTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserApplicationService userService;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldRegisterWork() throws Exception {
        // Given
        UserRegisterDto userRegisterDto = DataToTest.userRegisterDtoToSaveTest();
        when(this.userService.registerUser(any(UserRegisterDto.class))).then(invocation ->{
            UserRegisterDto u = invocation.getArgument(0);
            return u;
        });

        // when
        mvc.perform(post("/api/users/v1/register-user")
//                        .header("X-API-Version","1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegisterDto)))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("usertest2"))
//                .andExpect(jsonPath("$.password").value("usertest2"))
                .andExpect(jsonPath("$.email").value("test123@gmail.com"))
                .andExpect(jsonPath("$.firstname").value("Alexander"))
                .andExpect(jsonPath("$.lastname").value("Urbina"))
                .andExpect(jsonPath("$.country").value("Peru"))
                .andExpect(jsonPath("$.city").value("Lima"))
                .andExpect(jsonPath("$.birthDateUtc").value("1999-02-20T00:34:29.235Z"));

        verify(userService).registerUser(eq(userRegisterDto));
    }

    @Test
    void shouldFindUserById() throws Exception {
        // Given
        UserToFindDto userSaved = DataToTest.userToReturn();

        when(this.userService.findUserBasicInfoById(2L)).thenReturn(userSaved);

        // when
        mvc.perform(get("/api/users/v1/basic-user-info/2")
//                        .header("X-API-Version","1")
                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(userRegisterDto))
        )
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.username").value("usertest2"))
                .andExpect(jsonPath("$.country").value("Peru"))
                .andExpect(jsonPath("$.city").value("Lima"))
//                .andExpect(jsonPath("$.birthDate").value(userSaved.getBirthDate()))
//                .andExpect(jsonPath("$.accountCreationDate").value(userSaved.getAccountCreationDate()))
                .andExpect(jsonPath("$.photoUrl").value("/"));

        verify(userService).findUserBasicInfoById(2L);
    }
}
