package com.mvnnixbuyapi.userservice.mappers;

import com.mvnnixbuyapi.commons.dto.UserRegisterDto;
import com.mvnnixbuyapi.userservice.models.UserApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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

}
