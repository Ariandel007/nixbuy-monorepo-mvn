package com.mvnnixbuyapi.userservice.services.impl;

import com.mvnnixbuyapi.commons.dto.UserRegisterDto;
import com.mvnnixbuyapi.commons.dto.UserToFindDto;
import com.mvnnixbuyapi.userservice.exceptions.InvalidUserToRegisterException;
import com.mvnnixbuyapi.userservice.exceptions.UserAlreadyExistsException;
import com.mvnnixbuyapi.userservice.mappers.UserMapper;
import com.mvnnixbuyapi.userservice.models.UserApplication;
import com.mvnnixbuyapi.userservice.repositories.UserApplicationRepository;
import com.mvnnixbuyapi.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.*;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final Validator validator;
    private final PasswordEncoder passwordEncoder;
    private final UserApplicationRepository userApplicationRepository;

    @Autowired
    public UserServiceImpl(
            UserApplicationRepository userApplicationRepository,
            PasswordEncoder passwordEncoder,
            Validator validator) {
        this.userApplicationRepository = userApplicationRepository;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
    };

    @Override
    @Transactional(readOnly = false)
    public UserRegisterDto registerUser(UserRegisterDto userRegisterDto) {
       this.validateUserToRegister(userRegisterDto);

        Optional<UserApplication> optUserInBd = this.userApplicationRepository.findByUsername(userRegisterDto.getUsername());
        if(optUserInBd.isPresent()) {
            throw new UserAlreadyExistsException("USER_ALREADY_EXISTS", "Username already exists");
        }

        UserApplication userApplication = UserMapper.INSTANCE.mapUserRegisterDtoToUserApplication(userRegisterDto);

        userApplication.setBirthDate(Instant.parse(userRegisterDto.getBirthDateUtc()));

        // hashing password with bcrypt
        userApplication.setPassword(this.passwordEncoder.encode(userApplication.getPassword()));

        Instant today = Instant.now();
        userApplication.setAccountCreationDate(today);

        UserApplication userCreated = this.userApplicationRepository.save(userApplication);
        UserRegisterDto userRegisterDtoCreated = UserMapper.INSTANCE.mapUserApplicationToUserRegisterDto(userCreated);

        return userRegisterDtoCreated;
    }

    @Override
    @Transactional(readOnly = true)
    public UserToFindDto findUserBasicInfoById(Long id) {
        return this.userApplicationRepository.findUserToFindDto(id);
    }

    private void validateUserToRegister(UserRegisterDto userRegisterDto) {
        // Create a BindingResult to capture validation errors
        BindingResult errors = new BeanPropertyBindingResult(userRegisterDto, "userRegisterDto");
        // Perform validation
        validator.validate(userRegisterDto, errors);

        if (errors.hasErrors()) {
            FieldError usernameError = errors.getFieldError("username");
            if (usernameError != null) {
                String errorMessage = usernameError.getDefaultMessage();
                throw new InvalidUserToRegisterException("INVALID_USERNAME", errorMessage);
            }
            FieldError passwordError = errors.getFieldError("password");
            if (passwordError != null) {
                String errorMessage = passwordError.getDefaultMessage();
                throw new InvalidUserToRegisterException("INVALID_PASSWORD", errorMessage);
            }
            FieldError emailError = errors.getFieldError("email");
            if (emailError != null) {
                String errorMessage = emailError.getDefaultMessage();
                throw new InvalidUserToRegisterException("INVALID_EMAIL", errorMessage);
            }
            FieldError firstnameError = errors.getFieldError("firstname");
            if (firstnameError != null) {
                String errorMessage = firstnameError.getDefaultMessage();
                throw new InvalidUserToRegisterException("INVALID_FIRSTNAME", errorMessage);
            }
            FieldError lastnameError = errors.getFieldError("lastname");
            if (lastnameError != null) {
                String errorMessage = lastnameError.getDefaultMessage();
                throw new InvalidUserToRegisterException("INVALID_LASTNAME", errorMessage);
            }
            FieldError countryError = errors.getFieldError("country");
            if (countryError != null) {
                String errorMessage = countryError.getDefaultMessage();
                throw new InvalidUserToRegisterException("INVALID_COUNTRY", errorMessage);
            }
            FieldError cityError = errors.getFieldError("city");
            if (cityError != null) {
                String errorMessage = cityError.getDefaultMessage();
                throw new InvalidUserToRegisterException("INVALID_CITY", errorMessage);
            }
            FieldError birthDateUtcError = errors.getFieldError("birthDateUtc");
            if (birthDateUtcError != null) {
                String errorMessage = birthDateUtcError.getDefaultMessage();
                throw new InvalidUserToRegisterException("INVALID_BIRTHDATE", errorMessage);
            }
        }
    }
}
