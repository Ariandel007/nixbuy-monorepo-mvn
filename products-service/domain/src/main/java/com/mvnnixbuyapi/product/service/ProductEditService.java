package com.mvnnixbuyapi.product.service;

import com.mvnnixbuyapi.product.model.dto.command.ProductCreateCommand;
import com.mvnnixbuyapi.product.model.dto.command.ProductEditCommand;
import com.mvnnixbuyapi.product.model.entity.Product;
import com.mvnnixbuyapi.product.port.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductEditService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductEditService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product execute(ProductEditCommand productEditCommand) {
        var productToEdit = new Product().requestToEdit(productEditCommand);
        return this.productRepository.edit(productToEdit);
    }

}
