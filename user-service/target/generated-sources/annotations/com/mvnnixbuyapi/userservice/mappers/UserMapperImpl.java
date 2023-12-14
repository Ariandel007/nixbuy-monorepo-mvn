package com.mvnnixbuyapi.userservice.mappers;

import com.mvnnixbuyapi.userservice.dto.UserPhotoUpdated;
import com.mvnnixbuyapi.userservice.dto.UserRegisterDto;
import com.mvnnixbuyapi.userservice.dto.UserToUpdateDto;
import com.mvnnixbuyapi.userservice.models.UserApplication;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-09T19:39:08-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.36.0.v20231114-0937, environment: Java 17.0.9 (Eclipse Adoptium)"
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

    @Override
    public UserApplication mapUserToUpdateDtoToUserApplication(UserToUpdateDto userToUpdateDto, UserApplication userApplication) {
        if ( userToUpdateDto == null ) {
            return userApplication;
        }

        userApplication.setCountry( userToUpdateDto.getCountry() );
        userApplication.setCity( userToUpdateDto.getCity() );
        userApplication.setBirthDate( userToUpdateDto.getBirthDate() );
        userApplication.setAccountCreationDate( userToUpdateDto.getAccountCreationDate() );
        userApplication.setFirstname( userToUpdateDto.getFirstname() );
        userApplication.setLastname( userToUpdateDto.getLastname() );

        return userApplication;
    }

    @Override
    public UserToUpdateDto mapUserApplicationToUserToUpdateDto(UserApplication userApplication) {
        if ( userApplication == null ) {
            return null;
        }

        UserToUpdateDto userToUpdateDto = new UserToUpdateDto();

        userToUpdateDto.setAccountCreationDate( userApplication.getAccountCreationDate() );
        userToUpdateDto.setBirthDate( userApplication.getBirthDate() );
        userToUpdateDto.setCity( userApplication.getCity() );
        userToUpdateDto.setCountry( userApplication.getCountry() );
        userToUpdateDto.setFirstname( userApplication.getFirstname() );
        userToUpdateDto.setLastname( userApplication.getLastname() );

        return userToUpdateDto;
    }

    @Override
    public UserPhotoUpdated mapUserApplicationToUserPhotoUpdated(UserApplication userApplication) {
        if ( userApplication == null ) {
            return null;
        }

        UserPhotoUpdated userPhotoUpdated = new UserPhotoUpdated();

        userPhotoUpdated.setAccountCreationDate( userApplication.getAccountCreationDate() );
        userPhotoUpdated.setBirthDate( userApplication.getBirthDate() );
        userPhotoUpdated.setCity( userApplication.getCity() );
        userPhotoUpdated.setCountry( userApplication.getCountry() );
        userPhotoUpdated.setEmail( userApplication.getEmail() );
        userPhotoUpdated.setFirstname( userApplication.getFirstname() );
        userPhotoUpdated.setId( userApplication.getId() );
        userPhotoUpdated.setLastname( userApplication.getLastname() );
        userPhotoUpdated.setPhotoUrl( userApplication.getPhotoUrl() );
        userPhotoUpdated.setUsername( userApplication.getUsername() );

        return userPhotoUpdated;
    }
}
