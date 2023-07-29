package com.mvnnixbuyapi.userservice.unittesting;

import com.mvnnixbuyapi.userservice.data.DataToTest;
import com.mvnnixbuyapi.userservice.dto.UserRegisterDto;
import com.mvnnixbuyapi.userservice.exceptions.InvalidUserToRegisterException;
import com.mvnnixbuyapi.userservice.exceptions.UserAlreadyExistsException;
import com.mvnnixbuyapi.userservice.models.UserApplication;
import com.mvnnixbuyapi.userservice.repositories.UserApplicationRepository;
import com.mvnnixbuyapi.userservice.services.UserApplicationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Validator;

import javax.xml.crypto.Data;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserCreationTests {
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
    void testBlankUsername() {
        //Given
        UserRegisterDto userRegisterDto = DataToTest.userRegisterDtoToSaveTest();
        userRegisterDto.setUsername(null);

        this.commonGeneralInvalidUserToRegisterException(userRegisterDto, "Username is required");
    }

    @Test
    @DisplayName("Test insert empty password")
    void testBlankPassword() {
        //Given
        UserRegisterDto userRegisterDto = DataToTest.userRegisterDtoToSaveTest();
        userRegisterDto.setPassword(null);

        this.commonGeneralInvalidUserToRegisterException(userRegisterDto, "Password is required");
    }

    @Test
    @DisplayName("Test insert empty email")
    void testBlankEmail() {
        //Given
        UserRegisterDto userRegisterDto = DataToTest.userRegisterDtoToSaveTest();
        userRegisterDto.setEmail(null);

        this.commonGeneralInvalidUserToRegisterException(userRegisterDto, "Email is required");
    }

    @Test
    @DisplayName("Test insert empty firstname")
    void testBlankFirstname() {
        //Given
        UserRegisterDto userRegisterDto = DataToTest.userRegisterDtoToSaveTest();
        userRegisterDto.setFirstname(null);

        this.commonGeneralInvalidUserToRegisterException(userRegisterDto, "Firstname is required");
    }

    @Test
    @DisplayName("Test insert empty lastname")
    void testBlankLastname() {
        //Given
        UserRegisterDto userRegisterDto = DataToTest.userRegisterDtoToSaveTest();
        userRegisterDto.setLastname(null);

        this.commonGeneralInvalidUserToRegisterException(userRegisterDto, "Lastname is required");
    }

    @Test
    @DisplayName("Test insert empty country")
    void testBlankCountry() {
        //Given
        UserRegisterDto userRegisterDto = DataToTest.userRegisterDtoToSaveTest();
        userRegisterDto.setCountry(null);

        this.commonGeneralInvalidUserToRegisterException(userRegisterDto, "Country is required");
    }

    @Test
    @DisplayName("Test insert empty city")
    void testBlankCity() {
        //Given
        UserRegisterDto userRegisterDto = DataToTest.userRegisterDtoToSaveTest();
        userRegisterDto.setCity(null);

        this.commonGeneralInvalidUserToRegisterException(userRegisterDto, "City is required");

    }

    @Test
    @DisplayName("Test insert empty birthdate")
    void testBlankBirthDate() {
        //Given
        UserRegisterDto userRegisterDto = DataToTest.userRegisterDtoToSaveTest();
        userRegisterDto.setBirthDateUtc(null);

        this.commonGeneralInvalidUserToRegisterException(userRegisterDto, "birthdate is required");
    }

    private void commonGeneralInvalidUserToRegisterException(UserRegisterDto userRegisterDto, String errorMessage) {
        when(this.userApplicationRepository.findByUsername(eq("usertest"))).thenReturn(DataToTest.findOptUserApplicationTest());
        when(this.passwordEncoder.encode(anyString())).thenReturn("$2a$10$ruiC/sjn7YAJiPDD/G8VzO1SJLVjFJwRDopz5WljF.q8w.IdGvKQu");
        when(userApplicationRepository.save(any(UserApplication.class))).thenAnswer(invocation -> {
            UserApplication userApplication = invocation.getArgument(0);
            userApplication.setId(2L);
            return userApplication;
        });

        //When
        Throwable thrown = assertThrows(InvalidUserToRegisterException.class, ()-> {
            this.userService.registerUser(userRegisterDto);
        });

        //Then
        Assertions.assertEquals(errorMessage, thrown.getMessage());

    }

    @Test
    @DisplayName("Test insert successfully")
    void testSucessfullInsertion() {
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
    void testExistsUsername() {
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
