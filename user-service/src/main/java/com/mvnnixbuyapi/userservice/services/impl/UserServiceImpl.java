package com.mvnnixbuyapi.userservice.services.impl;

import com.mvnnixbuyapi.commons.dto.UserRegisterDto;
import com.mvnnixbuyapi.commons.dto.UserToFindDto;
import com.mvnnixbuyapi.userservice.mappers.UserMapper;
import com.mvnnixbuyapi.userservice.models.UserApplication;
import com.mvnnixbuyapi.userservice.repositories.UserApplicationRepository;
import com.mvnnixbuyapi.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserApplicationRepository userApplicationRepository;

    @Autowired
    public UserServiceImpl(
            UserApplicationRepository userApplicationRepository,
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
