package com.mvnnixbuyapi.keyProduct.model.entity;

import com.mvnnixbuyapi.keyProduct.model.dto.command.KeyCreateCommand;
import com.mvnnixbuyapi.keyProduct.model.entity.valueobjects.*;
import com.mvnnixbuyapi.platform.model.entity.Platform;
import com.mvnnixbuyapi.product.model.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
public class KeyProduct {
    private KeyProductId id;
    private KeyCode keyCode;
    private KeyStatus status;
    private KeyCreateDate createDate;
    private KeyActiveDate activeDate;
    private KeyInactiveDate inactiveDate;
    private KeySoldDate soldDate;
    private KeyProductProduct product;
    private KeyProductPlatform platform;

    public KeyProduct(
            Long id,
            String keyCode,
            BigDecimal price,
            String status,
            Instant createDate,
            Instant activeDate,
            Instant inactiveDate,
            Instant soldDate
    ) {
        this.id = new KeyProductId(id);
        this.keyCode = new KeyCode(keyCode);
        this.status = new KeyStatus(status);
        this.createDate = new KeyCreateDate(createDate);
        this.activeDate = new KeyActiveDate(activeDate);
        this.inactiveDate = new KeyInactiveDate(inactiveDate);
        this.soldDate = new KeySoldDate(soldDate);
    }

    public KeyProduct(
            Long id,
            String keyCode,
            BigDecimal price,
            String status,
            Instant createDate,
            Instant activeDate,
            Instant inactiveDate,
            Instant soldDate,
            Product product,
            Platform platform
    ) {
        this.id = new KeyProductId(id);
        this.keyCode = new KeyCode(keyCode);
        this.status = new KeyStatus(status);
        this.createDate = new KeyCreateDate(createDate);
        this.activeDate = new KeyActiveDate(activeDate);
        this.inactiveDate = new KeyInactiveDate(inactiveDate);
        this.soldDate = new KeySoldDate(soldDate);
        this.product = new KeyProductProduct(product);
        this.platform = new KeyProductPlatform(platform);
    }

    public KeyProduct requestToCreate(KeyCreateCommand keyCreateCommand) {
        this.keyCode = new KeyCode(keyCreateCommand.getKeyCode());
        this.status = new KeyStatus(keyCreateCommand.getStatus());
        return this;
    }

}
