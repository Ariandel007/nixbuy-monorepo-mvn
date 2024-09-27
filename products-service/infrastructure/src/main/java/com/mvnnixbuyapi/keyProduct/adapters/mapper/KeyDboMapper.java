package com.mvnnixbuyapi.keyProduct.adapters.mapper;

import com.mvnnixbuyapi.keyProduct.adapters.entity.KeyProductEntity;
import com.mvnnixbuyapi.keyProduct.model.entity.KeyProduct;
import com.mvnnixbuyapi.platform.model.entity.Platform;
import com.mvnnixbuyapi.product.model.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class KeyDboMapper {
    public KeyDboMapper() {
    }

    public KeyProductEntity domainToEntity(KeyProduct keyProduct){
        if (keyProduct == null) {
            return null;
        }
        return KeyProductEntity
                .builder()
                .id(keyProduct.getId())
                .keyCode(keyProduct.getKeyCode())
                .status(keyProduct.getStatus())
                .createDate(keyProduct.getCreateDate())
                .activeDate(keyProduct.getActiveDate())
                .inactiveDate(keyProduct.getInactiveDate())
                .soldDate(keyProduct.getSoldDate())
                .plattformId(keyProduct.getPlatform().getId())
                .productId(keyProduct.getProduct().getId())
                .build();
    }
    public KeyProduct entityToDomain(KeyProductEntity keyProductEntity) {
        if (keyProductEntity == null) {
            return null;
        }
        return new KeyProduct(
                keyProductEntity.getId(),
                keyProductEntity.getKeyCode(),
                keyProductEntity.getStatus(),
                keyProductEntity.getCreateDate(),
                keyProductEntity.getActiveDate(),
                keyProductEntity.getInactiveDate(),
                keyProductEntity.getSoldDate(),
                new Product(
                        keyProductEntity.getProductId(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                ),
                new Platform(
                        keyProductEntity.getId(),
                        null
                )
        );
    }
}
