package com.mvnnixbuyapi.product.service;

import com.mvnnixbuyapi.product.model.dto.command.ProductEditCommand;
import com.mvnnixbuyapi.product.model.entity.Product;
import com.mvnnixbuyapi.product.port.imageManagement.ProductImageManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductUpdatePhotoService {
    private final ProductImageManagement productImageManagement;

    @Autowired
    public ProductUpdatePhotoService(ProductImageManagement productImageManagement){
        this.productImageManagement = productImageManagement;
    }

    public String execute(ProductEditCommand productEditCommand) {
        var productToEdit = new Product().requestToEdit(productEditCommand);
        return this.productImageManagement.generateNewUrl(productToEdit, productEditCommand.getMainPhotoOfProductFile());
    }
}
