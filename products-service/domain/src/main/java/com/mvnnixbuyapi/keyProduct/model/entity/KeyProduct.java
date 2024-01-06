package com.mvnnixbuyapi.keyProduct.model.entity;

import com.mvnnixbuyapi.keyProduct.model.dto.command.KeyCreateCommand;
import com.mvnnixbuyapi.keyProduct.model.entity.enumfiles.KeyStatusEnum;
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
        this.createDate = new KeyCreateDate(Instant.now());
        if (this.status.status().equals(KeyStatusEnum.ACTIVE.toString())) {
            this.activeDate = new KeyActiveDate(Instant.now());
        } else {
            this.inactiveDate = new KeyInactiveDate(Instant.now());
        }
        this.product = new KeyProductProduct(new Product(
                keyCreateCommand.getIdProduct(),
                null,
                null,
                null,
                null,
                null,
                null
        ));
        this.platform = new KeyProductPlatform(new Platform(
                keyCreateCommand.getIdPlatform(),
                null
        ));
        return this;
    }


    public Long getId() {
        return id.keyId();
    }

    public String getKeyCode() {
        return keyCode.keyCode();
    }

    public String getStatus() {
        return status.status();
    }

    public Instant getCreateDate() {
        return createDate.createDate();
    }

    public Instant getActiveDate() {
        return activeDate.activeDate();
    }

    public Instant getInactiveDate() {
        return inactiveDate.inactiveDate();
    }

    public Instant getSoldDate() {
        return soldDate.soldDate();
    }

    public Product getProduct() {
        return product.product();
    }

    public Platform getPlatform() {
        return platform.platform();
    }
}
