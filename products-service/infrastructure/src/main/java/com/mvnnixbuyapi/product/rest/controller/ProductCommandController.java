package com.mvnnixbuyapi.product.rest.controller;

import com.mvnnixbuyapi.product.command.ProductCreateHandler;
import com.mvnnixbuyapi.product.model.dto.ProductDto;
import com.mvnnixbuyapi.product.model.dto.command.ProductCreateCommand;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/command-product-endpoint/")
public class ProductCommandController {
    private final ProductCreateHandler productCreateHandler;

    public ProductCommandController(ProductCreateHandler productCreateHandler) {
        this.productCreateHandler = productCreateHandler;
    }

    @PostMapping("create")
    public ProductDto create(@RequestBody ProductCreateCommand productCreateCommand){
        return this.productCreateHandler.execute(productCreateCommand);
    }

}
