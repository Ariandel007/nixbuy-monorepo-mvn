package com.mvnnixbuyapi.userservice.services.impl;

import com.mvnnixbuyapi.userservice.dto.*;
import com.mvnnixbuyapi.userservice.exceptions.InvalidPatternOfPasswordException;
import com.mvnnixbuyapi.userservice.exceptions.InvalidUserToRegisterException;
import com.mvnnixbuyapi.userservice.exceptions.UserAlreadyExistsException;
import com.mvnnixbuyapi.userservice.exceptions.UserToUpdateNotFoundException;
import com.mvnnixbuyapi.userservice.models.PasswordHistory;
import com.mvnnixbuyapi.userservice.repositories.PasswordHistoryRepository;
import com.mvnnixbuyapi.userservice.services.UploadToCloudService;
import com.mvnnixbuyapi.userservice.services.UserApplicationService;
import com.mvnnixbuyapi.userservice.components.TokenProvider;
import com.mvnnixbuyapi.userservice.dto.*;
import com.mvnnixbuyapi.userservice.exceptions.*;
import com.mvnnixbuyapi.userservice.mappers.UserMapper;
import com.mvnnixbuyapi.userservice.models.RoleApplication;
import com.mvnnixbuyapi.userservice.models.UserApplication;
import com.mvnnixbuyapi.userservice.repositories.UserApplicationRepository;
import com.mvnnixbuyapi.userservice.utils.UserServiceMessageErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserApplicationServiceImpl implements UserApplicationService {

    private final Validator validator;
    private final PasswordEncoder passwordEncoder;
    private final UserApplicationRepository userApplicationRepository;

    private final PasswordHistoryRepository passwordHistoryRepository;
    private final TokenProvider tokenProvider;

    private final UploadToCloudService uploadToCloudService;

    @Autowired
    public UserApplicationServiceImpl(
            UserApplicationRepository userApplicationRepository,
            PasswordEncoder passwordEncoder,
            Validator validator,
            TokenProvider tokenProvider,
            PasswordHistoryRepository passwordHistoryRepository,
            UploadToCloudService uploadToCloudService) {
        this.userApplicationRepository = userApplicationRepository;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
        this.tokenProvider = tokenProvider;
        this.passwordHistoryRepository = passwordHistoryRepository;
        this.uploadToCloudService = uploadToCloudService;
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
            // Get User
            UserApplication userApplication = userApplicationOptional.get();

            // INSERT OLD PASSWORD
            PasswordHistory passwordHistory = new PasswordHistory();
            passwordHistory.setPasswordStored(userApplication.getPassword());
            passwordHistory.setIdUserApp(userApplication.getId());
            Instant today = Instant.now();
            passwordHistory.setCreationDate(today);
            this.passwordHistoryRepository.save(passwordHistory);

            // UPDATE PASSWORD
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

    @Override
    @Transactional(readOnly = false)
    public UserPhotoUpdated uploadPhoto(Long userId, MultipartFile filePhoto) {
        String urlUserLogo = "/assets/default_user_login.png";
        if(filePhoto != null) {
            urlUserLogo = this.uploadToCloudService.uploadFileToCloudinary(filePhoto);
        }
        if(this.userApplicationRepository.findById(userId).isPresent()) {
            UserApplication userApplication = this.userApplicationRepository.findById(userId).get();
            userApplication.setPhotoUrl(urlUserLogo);
            UserApplication userApplicationCreated = this.userApplicationRepository.save(userApplication);
            return UserMapper.INSTANCE.mapUserApplicationToUserPhotoUpdated(userApplicationCreated);
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

    @Override
    @Transactional(readOnly = false)
    public UserToUpdateDto updateUserBasicInformation(Long userId, UserToUpdateDto userToUpdateDto) {
        Optional<UserApplication> userApplicationOptional = this.userApplicationRepository.findById(userId);
        if(userApplicationOptional.isPresent()) {
            // Get User
            UserApplication userApplication = userApplicationOptional.get();
            UserApplication userApplicationUpdated = UserMapper.INSTANCE
                    .mapUserToUpdateDtoToUserApplication(userToUpdateDto, userApplication);
            UserApplication userUpdated = this.userApplicationRepository.save(userApplicationUpdated);
            UserToUpdateDto userToReturn = UserMapper.INSTANCE.mapUserApplicationToUserToUpdateDto(userUpdated);
            return userToReturn;
        }
        throw new UserToUpdateNotFoundException(UserServiceMessageErrors.USER_TO_UPDATE_NOT_FOUND,
                UserServiceMessageErrors.USER_TO_UPDATE_NOT_FOUND_MSG);

    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDataWithRolesDto> listUserDataWithRolesDtos(Long cursorId){
        List<UserDataWithRolesDto> userDataWithRolesDtos = this.userApplicationRepository.listUserDataRoles(cursorId);
        Map<String, List<UserDataWithRolesDto>> groupedData = userDataWithRolesDtos.stream()
                .collect(Collectors.groupingBy(
                        userData -> userData.getId() + userData.getUsername() + userData.isBlocked()
                ));
        List<UserDataWithRolesDto> result = groupedData.values().stream()
                .map(group -> {
                    UserDataWithRolesDto combinedUserData = group.get(0); // Tomar el primer UserData del grupo como base

                    // Combinar roles si hay más de un elemento en el grupo
                    if (group.size() > 1) {
                        String combinedRoles = group.stream()
                                .map(UserDataWithRolesDto::getRoles)
                                .collect(Collectors.joining(", "));
                        combinedUserData.setRoles(combinedRoles);
                    }

                    return combinedUserData;
                })
                .collect(Collectors.toList());

        return result;
    }
}
