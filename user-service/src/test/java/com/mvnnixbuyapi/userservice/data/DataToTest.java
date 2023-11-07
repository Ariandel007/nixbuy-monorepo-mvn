package com.mvnnixbuyapi.userservice.data;

import com.mvnnixbuyapi.userservice.dto.UserPasswordToUpdateDto;
import com.mvnnixbuyapi.userservice.dto.UserRegisterDto;
import com.mvnnixbuyapi.userservice.dto.UserToFindDto;
import com.mvnnixbuyapi.userservice.models.UserApplication;

import java.time.Instant;
import java.util.Optional;

public class DataToTest {
    public static Optional<UserApplication> findOptUserApplicationTest() {
        UserApplication userApplication = new UserApplication();
        userApplication.setId(1L);
        userApplication.setUsername("usertest");
        userApplication.setPassword("$2a$10$s8WTqvgyM3tT4AvGkkDbpOwHD981ylV1/EMJQJtvWmi0O/Ncb.xZ.");
        userApplication.setEmail("test123@gmail.com");
        userApplication.setFirstname("Alexander");
        userApplication.setLastname("Urbina");
        userApplication.setCountry("Peru");
        userApplication.setCity("Lima");
        userApplication.setBirthDate(Instant.parse("1999-02-20T00:34:29.235Z"));
        userApplication.setAccountCreationDate(Instant.parse("2022-04-15T03:44:07.573716700Z"));
        return Optional.of(userApplication);
    }
    public static UserRegisterDto userRegisterDtoToSaveTest() {
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setUsername("usertest2");
        userRegisterDto.setPassword("ejemplo12345@A");
        userRegisterDto.setEmail("test123@gmail.com");
        userRegisterDto.setFirstname("Alexander");
        userRegisterDto.setLastname("Urbina");
        userRegisterDto.setCountry("Peru");
        userRegisterDto.setCity("Lima");
        userRegisterDto.setBirthDateUtc("1999-02-20T00:34:29.235Z");
        return userRegisterDto;
    }

    public static UserRegisterDto userRegisterDtoSaved() {
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setUsername("usertest2");
        userRegisterDto.setPassword("ejemplo12345@A");
        userRegisterDto.setEmail("test123@gmail.com");
        userRegisterDto.setFirstname("Alexander");
        userRegisterDto.setLastname("Urbina");
        userRegisterDto.setCountry("Peru");
        userRegisterDto.setCity("Lima");
        userRegisterDto.setBirthDateUtc("1999-02-20T00:34:29.235Z");
        return userRegisterDto;
    }

    public static UserApplication userSavedTest() {
        UserApplication userApplication = new UserApplication();
        userApplication.setId(2L);
        userApplication.setUsername("usertest2");
        userApplication.setPassword("$2a$10$s8WTqvgyM3tT4AvGkkDbpOwHD981ylV1/EMJQJtvWmi0O/Ncb.xZ.");
        userApplication.setEmail("test123@gmail.com");
        userApplication.setFirstname("Alexander");
        userApplication.setLastname("Urbina");
        userApplication.setCountry("Peru");
        userApplication.setCity("Lima");
        userApplication.setBirthDate(Instant.parse("1999-02-20T00:34:29.235Z"));
        Instant today = Instant.now();
        userApplication.setAccountCreationDate(today);
        return userApplication;
    }

    public static UserToFindDto userToReturn() {
        UserToFindDto userToFindDto = new UserToFindDto();
        userToFindDto.setId(2L);
        userToFindDto.setCity("Lima");
        userToFindDto.setCountry("Peru");
        userToFindDto.setUsername("usertest2");
        userToFindDto.setPhotoUrl("/");
        userToFindDto.setBirthDate(Instant.parse("1999-02-20T00:34:29.235Z"));
        userToFindDto.setAccountCreationDate(Instant.parse("2023-07-27T00:00:00.000Z"));
        return userToFindDto;
    }

    public static UserPasswordToUpdateDto passwordToUpdateDto() {
        UserPasswordToUpdateDto userPasswordToUpdateDto = new UserPasswordToUpdateDto();
        userPasswordToUpdateDto.setPassword("PruebaPassNueva1234@");
        return userPasswordToUpdateDto;
    }

    public static UserPasswordToUpdateDto emptyPasswordToUpdateDto() {
        UserPasswordToUpdateDto userPasswordToUpdateDto = new UserPasswordToUpdateDto();
        userPasswordToUpdateDto.setPassword("");
        return userPasswordToUpdateDto;
    }

    public static UserPasswordToUpdateDto passwordWithoutUpperCasesToUpdateDto() {
        UserPasswordToUpdateDto userPasswordToUpdateDto = new UserPasswordToUpdateDto();
        userPasswordToUpdateDto.setPassword("pruebanueva1234@");
        return userPasswordToUpdateDto;
    }

    public static UserPasswordToUpdateDto passwordWithoutLowerCasesToUpdateDto() {
        UserPasswordToUpdateDto userPasswordToUpdateDto = new UserPasswordToUpdateDto();
        userPasswordToUpdateDto.setPassword("PRUEBANUEVA1234@");
        return userPasswordToUpdateDto;
    }

    public static UserPasswordToUpdateDto passwordWithoutNumberToUpdateDto() {
        UserPasswordToUpdateDto userPasswordToUpdateDto = new UserPasswordToUpdateDto();
        userPasswordToUpdateDto.setPassword("PPRUEBANUEVA@");
        return userPasswordToUpdateDto;
    }

    public static UserPasswordToUpdateDto passwordWithoutSpecialCharactersToUpdateDto() {
        UserPasswordToUpdateDto userPasswordToUpdateDto = new UserPasswordToUpdateDto();
        userPasswordToUpdateDto.setPassword("pruebanueva12345");
        return userPasswordToUpdateDto;
    }

    public static UserPasswordToUpdateDto passwordWithLessThanTwelveCharactersToUpdateDto() {
        UserPasswordToUpdateDto userPasswordToUpdateDto = new UserPasswordToUpdateDto();
        userPasswordToUpdateDto.setPassword("PruebPaN12@");
        return userPasswordToUpdateDto;
    }
}
