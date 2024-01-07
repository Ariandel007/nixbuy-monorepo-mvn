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
    @Mapping(source = "keyCode", target = "keyCode")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "platform.id", target = "platformId")
    KeyToCreateDto toDtoCreate(KeyProduct domain);

}
