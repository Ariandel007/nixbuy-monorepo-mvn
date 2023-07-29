package com.mvnnixbuyapi.userservice.data;

import com.mvnnixbuyapi.userservice.dto.UserRegisterDto;
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
}
