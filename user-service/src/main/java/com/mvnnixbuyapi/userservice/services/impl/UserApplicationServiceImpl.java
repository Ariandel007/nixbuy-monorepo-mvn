package com.mvnnixbuyapi.userservice.services.impl;

import com.mvnnixbuyapi.userservice.components.TokenProvider;
import com.mvnnixbuyapi.userservice.dto.*;
import com.mvnnixbuyapi.userservice.exceptions.*;
import com.mvnnixbuyapi.userservice.mappers.UserMapper;
import com.mvnnixbuyapi.userservice.models.PasswordHistory;
import com.mvnnixbuyapi.userservice.models.RoleApplication;
import com.mvnnixbuyapi.userservice.models.UserApplication;
import com.mvnnixbuyapi.userservice.repositories.UserApplicationRepository;
import com.mvnnixbuyapi.userservice.services.UserApplicationService;
import com.mvnnixbuyapi.userservice.utils.UserServiceMessageErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserApplicationServiceImpl implements UserApplicationService {

    private final Validator validator;
    private final PasswordEncoder passwordEncoder;
    private final UserApplicationRepository userApplicationRepository;

    private final TokenProvider tokenProvider;

    @Autowired
    public UserApplicationServiceImpl(
            UserApplicationRepository userApplicationRepository,
            PasswordEncoder passwordEncoder,
            Validator validator,
            TokenProvider tokenProvider) {
        this.userApplicationRepository = userApplicationRepository;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
        this.tokenProvider = tokenProvider;
    };

    @Override
    @Transactional(readOnly = false)
    public UserRegisterDto registerUser(UserRegisterDto userRegisterDto) {
       this.validateUserToRegister(userRegisterDto);

        Optional<UserApplication> optUserInBd = this.userApplicationRepository.findByUsername(userRegisterDto.getUsername());
        if(optUserInBd.isPresent()) {
            throw new UserAlreadyExistsException(UserServiceMessageErrors.USER_ALREADY_EXISTS,
                    UserServiceMessageErrors.USER_ALREADY_EXISTS_MSG);
        }

        UserApplication userApplication = UserMapper.INSTANCE.mapUserRegisterDtoToUserApplication(userRegisterDto);

        userApplication.setBirthDate(Instant.parse(userRegisterDto.getBirthDateUtc()));

        // hashing password with bcrypt
        userApplication.setPassword(this.passwordEncoder.encode(userApplication.getPassword()));

        Instant today = Instant.now();
        userApplication.setAccountCreationDate(today);

        //Roles
        List<RoleApplication> roleList = new ArrayList<>();
        RoleApplication roleApplication = new RoleApplication(1L,"ROLE_USER");
        roleList.add(roleApplication);
        userApplication.setRoleApplicationList(roleList);

        UserApplication userCreated = this.userApplicationRepository.save(userApplication);
        UserRegisterDto userRegisterDtoCreated = UserMapper.INSTANCE.mapUserApplicationToUserRegisterDto(userCreated);
        userRegisterDtoCreated.setBirthDateUtc(userCreated.getBirthDate().toString());

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
                throw new InvalidUserToRegisterException(UserServiceMessageErrors.INVALID_USERNAME_CODE, errorMessage);
            }
            FieldError passwordError = errors.getFieldError("password");
            if (passwordError != null) {
                String errorMessage = passwordError.getDefaultMessage();
                throw new InvalidUserToRegisterException(UserServiceMessageErrors.INVALID_PASSWORD_CODE, errorMessage);
            }
            FieldError emailError = errors.getFieldError("email");
            if (emailError != null) {
                String errorMessage = emailError.getDefaultMessage();
                throw new InvalidUserToRegisterException(UserServiceMessageErrors.INVALID_EMAIL_CODE, errorMessage);
            }
            FieldError firstnameError = errors.getFieldError("firstname");
            if (firstnameError != null) {
                String errorMessage = firstnameError.getDefaultMessage();
                throw new InvalidUserToRegisterException(UserServiceMessageErrors.INVALID_FIRSTNAME_CODE, errorMessage);
            }
            FieldError lastnameError = errors.getFieldError("lastname");
            if (lastnameError != null) {
                String errorMessage = lastnameError.getDefaultMessage();
                throw new InvalidUserToRegisterException(UserServiceMessageErrors.INVALID_LASTNAME_CODE, errorMessage);
            }
            FieldError countryError = errors.getFieldError("country");
            if (countryError != null) {
                String errorMessage = countryError.getDefaultMessage();
                throw new InvalidUserToRegisterException(UserServiceMessageErrors.INVALID_COUNTRY_CODE, errorMessage);
            }
            FieldError cityError = errors.getFieldError("city");
            if (cityError != null) {
                String errorMessage = cityError.getDefaultMessage();
                throw new InvalidUserToRegisterException(UserServiceMessageErrors.INVALID_CITY_CODE, errorMessage);
            }
            FieldError birthDateUtcError = errors.getFieldError("birthDateUtc");
            if (birthDateUtcError != null) {
                String errorMessage = birthDateUtcError.getDefaultMessage();
                throw new InvalidUserToRegisterException(UserServiceMessageErrors.INVALID_BIRTHDATE_CODE, errorMessage);
            }
        }
    }

    @Override
    @Transactional(readOnly = false)
    public UserDataToPasswordUpdatedDto updateUserPassword(Long userId, UserPasswordToUpdateDto userToUpdateDto) {
        Optional<UserApplication> userApplicationOptional = this.userApplicationRepository.findById(userId);
        if(userApplicationOptional.isPresent()) {
            this.validatePasswordToUpdate(userToUpdateDto);
            UserApplication userApplication = userApplicationOptional.get();
            userApplication.setPassword(this.passwordEncoder.encode(userToUpdateDto.getPassword()));
            userApplication.setAttemps((short) 0);
            this.userApplicationRepository.save(userApplication);

            UserDataToPasswordUpdatedDto userDataToPasswordUpdatedDto = new UserDataToPasswordUpdatedDto();
            userDataToPasswordUpdatedDto.setId(userApplication.getId());
            userDataToPasswordUpdatedDto.setUsername(userApplication.getUsername());
            return userDataToPasswordUpdatedDto;
        }
        throw new UserToUpdateNotFoundException(UserServiceMessageErrors.USER_TO_UPDATE_NOT_FOUND,
                UserServiceMessageErrors.USER_TO_UPDATE_NOT_FOUND_MSG);
    }

    @Override
    @Transactional(readOnly = false)
    public AuthTokenDto generateToken(LoginUserDto loginUser) {
        Optional<UserApplication> userApplicationOptional =
                this.userApplicationRepository.findByUsername(loginUser.getUsername());

        if(userApplicationOptional.isPresent()) {
            UserApplication userApplication = userApplicationOptional.get();
            userApplication.setAttemps((short) (userApplication.getAttemps() + 1));
            if(this.passwordEncoder.matches(loginUser.getPassword(), userApplication.getPassword()) ) {
                userApplication.setAttemps((short) 0);
                String token = this.tokenProvider.generateToken(userApplication);
                this.userApplicationRepository.save(userApplication);
                return new AuthTokenDto(token);
            } else {
                return null;
            }
        }

        return null;
    }

    private void validatePasswordToUpdate(UserPasswordToUpdateDto userToUpdateDto) {
        // Create a BindingResult to capture validation errors
        BindingResult errors = new BeanPropertyBindingResult(userToUpdateDto, "userToUpdateDto");
        // Perform validation
        validator.validate(userToUpdateDto, errors);

        if (errors.hasErrors()) {
            FieldError passwordError = errors.getFieldError("password");
            if (passwordError != null) {
                String errorMessage = passwordError.getDefaultMessage();
                throw new InvalidPatternOfPasswordException(UserServiceMessageErrors.INVALID_PATTERN_OF_PASSWORD, errorMessage);
            }
        }
    }
}
