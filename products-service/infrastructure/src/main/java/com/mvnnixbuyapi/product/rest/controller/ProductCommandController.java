package com.mvnnixbuyapi.product.rest.controller;

import com.mvnnixbuyapi.commons.dtos.response.GenericResponseForBody;
import com.mvnnixbuyapi.commons.monads.ResultMonad;
import com.mvnnixbuyapi.product.command.ProductCreateHandler;
import com.mvnnixbuyapi.product.model.dto.ProductDto;
import com.mvnnixbuyapi.product.model.dto.command.ProductCreateCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/command-product-endpoint")
public class ProductCommandController {
    private final ProductCreateHandler productCreateHandler;

    public ProductCommandController(ProductCreateHandler productCreateHandler) {
        this.productCreateHandler = productCreateHandler;
    }

    @PostMapping("/v1/create-product")
    public ResponseEntity<GenericResponseForBody<ProductDto>> createProduct(
            @RequestBody ProductCreateCommand productCreateCommand
    ){
        ResultMonad<ProductDto> productDtoResult = this.productCreateHandler.execute(productCreateCommand);

        if(productDtoResult.isError()){
            return ResponseEntity.badRequest().body(
                    new GenericResponseForBody<>(
                            productDtoResult.getError(),
                            productDtoResult.getError()
                    )
            );
        } else {
            return ResponseEntity.ok().body(
                    new GenericResponseForBody<>(
                            productDtoResult.getError(),
                            productDtoResult.getError(),
                            productDtoResult.getValue()
                    )

            );
        }
    }

}
