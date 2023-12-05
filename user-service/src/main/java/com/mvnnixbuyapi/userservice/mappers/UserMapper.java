package com.mvnnixbuyapi.userservice.mappers;

import com.mvnnixbuyapi.userservice.dto.UserPhotoUpdated;
import com.mvnnixbuyapi.userservice.dto.UserRegisterDto;
import com.mvnnixbuyapi.userservice.dto.UserToUpdateDto;
import com.mvnnixbuyapi.userservice.models.UserApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "blocked", ignore = true)
    @Mapping(target = "authType", ignore = true)
    @Mapping(target = "attemps", ignore = true)
    @Mapping(target = "accountCreationDate", ignore = true)
    @Mapping(target = "birthDate", ignore = true)
    @Mapping(target = "photoUrl", ignore = true)
    @Mapping(target = "passwordHistoryList", ignore = true)
    @Mapping(target = "roleApplicationList", ignore = true)
    UserApplication mapUserRegisterDtoToUserApplication(UserRegisterDto dto);

    UserRegisterDto mapUserApplicationToUserRegisterDto(UserApplication dto);

    // Mapeo de atributos comunes, es como un merge del contenido del source al target :D
    @Mapping(source = "userToUpdateDto.country", target = "country")
    @Mapping(source = "userToUpdateDto.city", target = "city")
    @Mapping(source = "userToUpdateDto.birthDate", target = "birthDate")
    @Mapping(source = "userToUpdateDto.accountCreationDate", target = "accountCreationDate")
    @Mapping(source = "userToUpdateDto.firstname", target = "firstname")
    @Mapping(source = "userToUpdateDto.lastname", target = "lastname")
    UserApplication mapUserToUpdateDtoToUserApplication(UserToUpdateDto userToUpdateDto, @MappingTarget UserApplication userApplication);

    UserToUpdateDto mapUserApplicationToUserToUpdateDto(UserApplication userApplication);


    UserPhotoUpdated mapUserApplicationToUserPhotoUpdated(UserApplication userApplication);

}
