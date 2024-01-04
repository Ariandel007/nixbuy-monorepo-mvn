package com.mvnnixbuyapi.product.mapper;

import com.mvnnixbuyapi.product.model.dto.ProductDto;
import com.mvnnixbuyapi.product.model.dto.ProductToEditDto;
import com.mvnnixbuyapi.product.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
public interface ProductDtoMapper {
    ProductDtoMapper INSTANCE = Mappers.getMapper(ProductDtoMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    ProductDto toDto(Product domain);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "urlImage", target = "urlImage")
    @Mapping(source = "price", target = "price")
    ProductToEditDto toProductEditDto(Product domain);

}
