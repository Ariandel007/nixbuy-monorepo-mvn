package com.mvnnixbuyapi.product.model.entity;

import com.mvnnixbuyapi.product.model.dto.command.ProductCreateCommand;
import com.mvnnixbuyapi.product.model.dto.command.ProductEditCommand;
import com.mvnnixbuyapi.product.model.entity.valueobjects.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class Product{
    private ProductId id;
    private ProductName name;
    private ProductDescription description;
    private ProductUrlImage urlImage;
    private ProductCreationDate creationDate;
    private ProductUpdateDate updateDate;


    public Product(
            Long id,
            String name,
            String description,
            String urlImage,
            Instant creationDate,
            Instant updateDate
    ){
        this.id = new ProductId(id);
        this.name = new ProductName(name);
        this.description = new ProductDescription(description);
        this.urlImage = new ProductUrlImage(urlImage);
        this.creationDate = new ProductCreationDate(creationDate);
        this.updateDate = new ProductUpdateDate(updateDate);
    }



    public Product requestToCreate(ProductCreateCommand productCreateCommand){
        this.name = new ProductName(productCreateCommand.getName());
        this.description = new ProductDescription(productCreateCommand.getDescription());
        this.urlImage = new ProductUrlImage("/default_product.png");
        this.creationDate = new ProductCreationDate(Instant.now());
        this.updateDate = new ProductUpdateDate(Instant.now());
        return this;
    }

    public Product requestToEdit(ProductEditCommand productEditCommand){
        this.id = new ProductId(productEditCommand.getId());
        this.name = new ProductName(productEditCommand.getName());
        this.description = new ProductDescription(productEditCommand.getDescription());
        this.urlImage = new ProductUrlImage(productEditCommand.getUrlImage());
        this.creationDate = new ProductCreationDate(Instant.now());
        this.updateDate = new ProductUpdateDate(Instant.now());
        return this;
    }

    public Long getId() {
        if(id == null)
            return null;
        return id.id();
    }

    public String getName() {
        return name.name();
    }

    public String getDescription() {
        return description.description();
    }

    public String getUrlImage() {
        return urlImage.urlImage();
    }

    public Instant getCreationDate() {
        return creationDate.creationDate();
    }

    public Instant getUpdateDate() {
        return updateDate.updateDate();
    }

}
