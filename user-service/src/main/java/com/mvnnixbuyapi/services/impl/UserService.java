package com.mvnnixbuyapi.services.impl;

import com.mvnnixbuyapi.dto.UserRegisterDto;
import com.mvnnixbuyapi.dto.UserToFindDto;
import com.mvnnixbuyapi.mappers.UserMapper;
import com.mvnnixbuyapi.models.UserApplication;
import com.mvnnixbuyapi.repositories.IUserApplicationRepository;
import com.mvnnixbuyapi.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

public class UserService implements IUserService {
    private final PasswordEncoder passwordEncoder;
    private final IUserApplicationRepository userApplicationRepository;

    @Autowired
    public UserService(
            IUserApplicationRepository userApplicationRepository,
            PasswordEncoder passwordEncoder) {
        this.userApplicationRepository = userApplicationRepository;
        this.passwordEncoder = passwordEncoder;
    };
    @Override
    @Transactional(readOnly = false)
    public UserRegisterDto registerUser(UserRegisterDto userRegisterDto) {
        // todo: dto's validations
        UserApplication userApplication = UserMapper.INSTANCE.mapUserRegisterDtoToUserApplication(userRegisterDto);

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
}
