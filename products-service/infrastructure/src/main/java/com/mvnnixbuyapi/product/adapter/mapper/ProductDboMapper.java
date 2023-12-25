package com.mvnnixbuyapi.product.adapter.mapper;

import com.mvnnixbuyapi.product.adapter.entity.ProductEntity;
import com.mvnnixbuyapi.product.model.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDboMapper {
    public ProductDboMapper(){

    }
    public ProductEntity domainToEntity(Product productDomain){
        if(productDomain == null){
            return null;
        }
        return ProductEntity.
                builder()
                .id(productDomain.getId())
                .name(productDomain.getName())
                .description(productDomain.getDescription())
                .urlImage(productDomain.getUrlImage())
                .creationDate(productDomain.getCreationDate())
                .updateDate(productDomain.getUpdateDate())
                .build();
    }

    public Product entityToProduct(ProductEntity productEntity){
        if(productEntity == null){
            return null;
        }
        return new Product(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getUrlImage(),
                productEntity.getCreationDate(),
                productEntity.getUpdateDate()
        );
    }
}
