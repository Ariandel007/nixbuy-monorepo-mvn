package com.mvnnixbuyapi.product.service;

import com.mvnnixbuyapi.product.model.dto.command.ProductCreateCommand;
import com.mvnnixbuyapi.product.model.entity.Product;
import com.mvnnixbuyapi.product.port.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductCreateService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductCreateService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product execute(ProductCreateCommand productCreateCommand) {
        var productToCreate = new Product().requestToCreate(productCreateCommand);
        return this.productRepository.create(productToCreate);
    }

}
