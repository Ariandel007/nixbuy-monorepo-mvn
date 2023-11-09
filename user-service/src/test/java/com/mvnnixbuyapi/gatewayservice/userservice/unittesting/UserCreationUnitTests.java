package com.mvnnixbuyapi.gatewayservice.userservice.unittesting;

import com.mvnnixbuyapi.gatewayservice.commons.exceptions.GeneralException;
import com.mvnnixbuyapi.gatewayservice.userservice.data.DataToTest;
import com.mvnnixbuyapi.gatewayservice.userservice.dto.UserRegisterDto;
import com.mvnnixbuyapi.gatewayservice.userservice.models.UserApplication;
import com.mvnnixbuyapi.gatewayservice.userservice.services.UserApplicationService;
import com.mvnnixbuyapi.gatewayservice.userservice.services.impl.UserApplicationServiceImpl;
import com.mvnnixbuyapi.gatewayservice.userservice.exceptions.InvalidUserToRegisterException;
import com.mvnnixbuyapi.gatewayservice.userservice.exceptions.UserAlreadyExistsException;
import com.mvnnixbuyapi.gatewayservice.userservice.repositories.UserApplicationRepository;
import com.mvnnixbuyapi.gatewayservice.userservice.utils.UserServiceMessageErrors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {
        PasswordEncoder.class,
        UserApplicationRepository.class,
        UserApplicationService.class,
        UserApplicationServiceImpl.class,
        LocalValidatorFactoryBean.class
})
public class UserCreationUnitTests {
//    @MockBean
//    Validator validator;
    @MockBean
    PasswordEncoder passwordEncoder;
    @MockBean
    UserApplicationRepository userApplicationRepository;

    @Autowired
    UserApplicationService userService;

    @Test
    @DisplayName("Test insert empty username")
    void shouldFailForBlankUsername() {
        //Given
        UserRegisterDto userRegisterDto = DataToTest.userRegisterDtoToSaveTest();
        userRegisterDto.setUsername(null);

        this.commonGeneralInvalidUserToRegisterException(userRegisterDto, UserServiceMessageErrors.INVALID_USERNAME_CODE, "Username is required");
    }

    @Test
    @DisplayName("Test insert empty password")
    void shouldFailForBlankPassword() {
        //Given
        UserRegisterDto userRegisterDto = DataToTest.userRegisterDtoToSaveTest();
        userRegisterDto.setPassword(null);

        this.commonGeneralInvalidUserToRegisterException(userRegisterDto, UserServiceMessageErrors.INVALID_PASSWORD_CODE, "Password is required");
    }

    @Test
    @DisplayName("Test insert empty email")
    void shouldFailForBlankEmail() {
        //Given
        UserRegisterDto userRegisterDto = DataToTest.userRegisterDtoToSaveTest();
        userRegisterDto.setEmail(null);

        this.commonGeneralInvalidUserToRegisterException(userRegisterDto, UserServiceMessageErrors.INVALID_EMAIL_CODE, "Email is required");
    }

    @Test
    @DisplayName("Test insert empty firstname")
    void shouldFailForBlankFirstname() {
        //Given
        UserRegisterDto userRegisterDto = DataToTest.userRegisterDtoToSaveTest();
        userRegisterDto.setFirstname(null);

        this.commonGeneralInvalidUserToRegisterException(userRegisterDto, UserServiceMessageErrors.INVALID_FIRSTNAME_CODE, "Firstname is required");
    }

    @Test
    @DisplayName("Test insert empty lastname")
    void shouldFailForBlankLastname() {
        //Given
        UserRegisterDto userRegisterDto = DataToTest.userRegisterDtoToSaveTest();
        userRegisterDto.setLastname(null);

        this.commonGeneralInvalidUserToRegisterException(userRegisterDto, UserServiceMessageErrors.INVALID_LASTNAME_CODE, "Lastname is required");
    }

    @Test
    @DisplayName("Test insert empty country")
    void shouldFailForBlankCountry() {
        //Given
        UserRegisterDto userRegisterDto = DataToTest.userRegisterDtoToSaveTest();
        userRegisterDto.setCountry(null);

        this.commonGeneralInvalidUserToRegisterException(userRegisterDto, UserServiceMessageErrors.INVALID_COUNTRY_CODE,"Country is required");
    }

    @Test
    @DisplayName("Test insert empty city")
    void shouldFailForBlankCity() {
        //Given
        UserRegisterDto userRegisterDto = DataToTest.userRegisterDtoToSaveTest();
        userRegisterDto.setCity(null);

        this.commonGeneralInvalidUserToRegisterException(userRegisterDto, UserServiceMessageErrors.INVALID_CITY_CODE,"City is required");

    }

    @Test
    @DisplayName("Test insert empty birthdate")
    void shouldFailForBlankBirthDate() {
        //Given
        UserRegisterDto userRegisterDto = DataToTest.userRegisterDtoToSaveTest();
        userRegisterDto.setBirthDateUtc(null);

        this.commonGeneralInvalidUserToRegisterException(userRegisterDto, UserServiceMessageErrors.INVALID_BIRTHDATE_CODE, "birthdate is required");
    }

    private void commonGeneralInvalidUserToRegisterException(UserRegisterDto userRegisterDto, String errorCode, String errorMessage) {
        when(this.userApplicationRepository.findByUsername(eq("usertest"))).thenReturn(DataToTest.findOptUserApplicationTest());
        when(this.passwordEncoder.encode(anyString())).thenReturn("$2a$10$ruiC/sjn7YAJiPDD/G8VzO1SJLVjFJwRDopz5WljF.q8w.IdGvKQu");
        when(userApplicationRepository.save(any(UserApplication.class))).thenAnswer(invocation -> {
            UserApplication userApplication = invocation.getArgument(0);
            userApplication.setId(2L);
            return userApplication;
        });

        //When
        GeneralException exception = assertThrows(InvalidUserToRegisterException.class, ()-> {
            this.userService.registerUser(userRegisterDto);
        });

        //Then
        Assertions.assertEquals(errorMessage, exception.getMessage());
        Assertions.assertEquals(errorCode, exception.getErrorCode());

    }

    @Test
    @DisplayName("Test insert successfully")
    void shouldSucessfullyInsertUser() {
        //Given
        UserRegisterDto userRegisterDto = DataToTest.userRegisterDtoToSaveTest();

        when(this.userApplicationRepository.findByUsername(eq("usertest"))).thenReturn(DataToTest.findOptUserApplicationTest());
        when(this.passwordEncoder.encode(anyString())).thenReturn("$2a$10$ruiC/sjn7YAJiPDD/G8VzO1SJLVjFJwRDopz5WljF.q8w.IdGvKQu");
        when(userApplicationRepository.save(any(UserApplication.class))).thenAnswer(invocation -> {
            UserApplication userApplication = invocation.getArgument(0);
            userApplication.setId(2L);
            return userApplication;
        });

        //When
        UserRegisterDto userCreated = this.userService.registerUser(userRegisterDto);

        //Then
        Assertions.assertEquals("usertest2", userCreated.getUsername());
    }

    @Test
    @DisplayName("Test user already exists")
    void shouldFailForExistsUsername() {
        //Given
        UserRegisterDto userRegisterDto = DataToTest.userRegisterDtoToSaveTest();
        userRegisterDto.setUsername("usertest");

        when(this.userApplicationRepository.findByUsername(eq("usertest"))).thenReturn(DataToTest.findOptUserApplicationTest());
        when(this.passwordEncoder.encode(anyString())).thenReturn("$2a$10$ruiC/sjn7YAJiPDD/G8VzO1SJLVjFJwRDopz5WljF.q8w.IdGvKQu");
        when(userApplicationRepository.save(any(UserApplication.class))).thenAnswer(invocation -> {
            UserApplication userApplication = invocation.getArgument(0);
            userApplication.setId(2L);
            return userApplication;
        });

        //When
        Throwable thrown = assertThrows(UserAlreadyExistsException.class, ()-> {
            this.userService.registerUser(userRegisterDto);
        });

        //Then
        Assertions.assertEquals("Username already exists", thrown.getMessage());
    }
}
