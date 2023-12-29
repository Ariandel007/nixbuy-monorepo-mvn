package com.mvnnixbuyapi.product.service;

import com.mvnnixbuyapi.commons.monads.ResultMonad;
import com.mvnnixbuyapi.product.model.dto.command.ProductCreateCommand;
import com.mvnnixbuyapi.product.model.dto.command.ProductEditCommand;
import com.mvnnixbuyapi.product.model.entity.Product;
import com.mvnnixbuyapi.product.port.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductEditService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductEditService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResultMonad<Product> execute(ProductEditCommand productEditCommand) {
        var productToEdit = new Product().requestToEdit(productEditCommand);
        var productInBD = this.productRepository.find(productToEdit);
        if(productInBD == null) {
            return ResultMonad.error("ERROR_PRODUCT_TO_EDIT_NOT_FOUNDED");
        }
        return ResultMonad.ok(this.productRepository.edit(productToEdit));
    }

}
