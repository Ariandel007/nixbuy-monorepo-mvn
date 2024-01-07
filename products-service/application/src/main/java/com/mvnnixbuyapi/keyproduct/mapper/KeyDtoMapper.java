package com.mvnnixbuyapi.keyproduct.mapper;

import com.mvnnixbuyapi.keyProduct.model.dto.KeyToCreateDto;
import com.mvnnixbuyapi.keyProduct.model.entity.KeyProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface KeyDtoMapper {
    KeyDtoMapper INSTANCE = Mappers.getMapper(KeyDtoMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    KeyToCreateDto toDtoCreate(KeyProduct domain);

}
