package com.mvnnixbuyapi.userservice.mappers;

import com.mvnnixbuyapi.userservice.dto.UserRegisterDto;
import com.mvnnixbuyapi.userservice.models.UserApplication;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-12T01:03:44-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.35.0.v20230814-2020, environment: Java 17.0.8.1 (Eclipse Adoptium)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserApplication mapUserRegisterDtoToUserApplication(UserRegisterDto dto) {
        if ( dto == null ) {
            return null;
        }

        UserApplication userApplication = new UserApplication();

        userApplication.setCity( dto.getCity() );
        userApplication.setCountry( dto.getCountry() );
        userApplication.setEmail( dto.getEmail() );
        userApplication.setFirstname( dto.getFirstname() );
        userApplication.setLastname( dto.getLastname() );
        userApplication.setPassword( dto.getPassword() );
        userApplication.setUsername( dto.getUsername() );

        return userApplication;
    }

    @Override
    public UserRegisterDto mapUserApplicationToUserRegisterDto(UserApplication dto) {
        if ( dto == null ) {
            return null;
        }

        UserRegisterDto userRegisterDto = new UserRegisterDto();

        userRegisterDto.setCity( dto.getCity() );
        userRegisterDto.setCountry( dto.getCountry() );
        userRegisterDto.setEmail( dto.getEmail() );
        userRegisterDto.setFirstname( dto.getFirstname() );
        userRegisterDto.setLastname( dto.getLastname() );
        userRegisterDto.setPassword( dto.getPassword() );
        userRegisterDto.setUsername( dto.getUsername() );

        return userRegisterDto;
    }
}
