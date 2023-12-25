package com.mvnnixbuyapi.product.mapper;

import com.mvnnixbuyapi.product.model.dto.ProductDto;
import com.mvnnixbuyapi.product.model.entity.Product;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-24T22:59:44-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (Eclipse Adoptium)"
)
public class ProductDtoMapperImpl implements ProductDtoMapper {

    @Override
    public ProductDto toDto(Product domain) {
        if ( domain == null ) {
            return null;
        }

        ProductDto productDto = new ProductDto();

        productDto.setId( domain.getId() );
        productDto.setName( domain.getName() );
        productDto.setDescription( domain.getDescription() );

        return productDto;
    }
}
