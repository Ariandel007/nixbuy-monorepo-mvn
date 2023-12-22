package com.mvnnixbuyapi.product.model.entity;

import com.mvnnixbuyapi.product.model.dto.command.ProductCreateCommand;
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
        this.name = new ProductName(productCreateCommand.name());
        this.description = new ProductDescription(productCreateCommand.description());
        this.creationDate = new ProductCreationDate(Instant.now());
        this.updateDate = new ProductUpdateDate(Instant.now());
        return this;
    }

}
