package com.mvnnixbuyapi.mappers;

import com.mvnnixbuyapi.dto.UserRegisterDto;
import com.mvnnixbuyapi.models.UserApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "isBlocked", ignore = true)
    @Mapping(target = "authType", ignore = true)
    @Mapping(target = "attemps", ignore = true)
    @Mapping(target = "accountCreationDate", ignore = true)
    @Mapping(target = "photoUrl", ignore = true)
    @Mapping(target = "passwordHistoryList", ignore = true)
    @Mapping(target = "roleApplicationList", ignore = true)
    UserApplication mapUserRegisterDtoToUserApplication(UserRegisterDto dto);

    UserRegisterDto mapUserApplicationToUserRegisterDto(UserApplication dto);

}
