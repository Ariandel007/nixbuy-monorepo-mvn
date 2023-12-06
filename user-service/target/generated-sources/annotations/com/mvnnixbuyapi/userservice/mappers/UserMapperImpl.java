package com.mvnnixbuyapi.userservice.mappers;

import com.mvnnixbuyapi.userservice.dto.UserPhotoUpdated;
import com.mvnnixbuyapi.userservice.dto.UserRegisterDto;
import com.mvnnixbuyapi.userservice.dto.UserToUpdateDto;
import com.mvnnixbuyapi.userservice.models.UserApplication;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-05T23:29:49-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (Eclipse Adoptium)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserApplication mapUserRegisterDtoToUserApplication(UserRegisterDto dto) {
        if ( dto == null ) {
            return null;
        }

        UserApplication userApplication = new UserApplication();

        userApplication.setUsername( dto.getUsername() );
        userApplication.setPassword( dto.getPassword() );
        userApplication.setEmail( dto.getEmail() );
        userApplication.setFirstname( dto.getFirstname() );
        userApplication.setLastname( dto.getLastname() );
        userApplication.setCountry( dto.getCountry() );
        userApplication.setCity( dto.getCity() );

        return userApplication;
    }

    @Override
    public UserRegisterDto mapUserApplicationToUserRegisterDto(UserApplication dto) {
        if ( dto == null ) {
            return null;
        }

        UserRegisterDto userRegisterDto = new UserRegisterDto();

        userRegisterDto.setUsername( dto.getUsername() );
        userRegisterDto.setPassword( dto.getPassword() );
        userRegisterDto.setEmail( dto.getEmail() );
        userRegisterDto.setFirstname( dto.getFirstname() );
        userRegisterDto.setLastname( dto.getLastname() );
        userRegisterDto.setCountry( dto.getCountry() );
        userRegisterDto.setCity( dto.getCity() );

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

        userToUpdateDto.setCountry( userApplication.getCountry() );
        userToUpdateDto.setCity( userApplication.getCity() );
        userToUpdateDto.setBirthDate( userApplication.getBirthDate() );
        userToUpdateDto.setAccountCreationDate( userApplication.getAccountCreationDate() );
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

        userPhotoUpdated.setId( userApplication.getId() );
        userPhotoUpdated.setUsername( userApplication.getUsername() );
        userPhotoUpdated.setEmail( userApplication.getEmail() );
        userPhotoUpdated.setFirstname( userApplication.getFirstname() );
        userPhotoUpdated.setLastname( userApplication.getLastname() );
        userPhotoUpdated.setCountry( userApplication.getCountry() );
        userPhotoUpdated.setCity( userApplication.getCity() );
        userPhotoUpdated.setBirthDate( userApplication.getBirthDate() );
        userPhotoUpdated.setAccountCreationDate( userApplication.getAccountCreationDate() );
        userPhotoUpdated.setPhotoUrl( userApplication.getPhotoUrl() );

        return userPhotoUpdated;
    }
}
