package com.mvnnixbuyapi.product.rest.controller;

import com.mvnnixbuyapi.commons.dtos.response.GenericResponseForBody;
import com.mvnnixbuyapi.commons.monads.ResultMonad;
import com.mvnnixbuyapi.product.command.ProductCreateHandler;
import com.mvnnixbuyapi.product.model.dto.ProductDto;
import com.mvnnixbuyapi.product.model.dto.command.ProductCreateCommand;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/command-product-endpoint")
public class ProductCommandController {
    private final ProductCreateHandler productCreateHandler;

    @Autowired
    public ProductCommandController(ProductCreateHandler productCreateHandler) {
        this.productCreateHandler = productCreateHandler;
    }

    @PostMapping("/v1/create-product")
    public ResponseEntity<GenericResponseForBody<ProductDto>> createProduct(
            @RequestBody @Valid ProductCreateCommand productCreateCommand,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(
                    new GenericResponseForBody<>(
                            errors
                    )
            );
        }

        ResultMonad<ProductDto> productDtoResult = this.productCreateHandler.execute(productCreateCommand);

        if(productDtoResult.isError()){
            return ResponseEntity.badRequest().body(
                    new GenericResponseForBody<>(
                            List.of(productDtoResult.getError())
                    )
            );
        } else {
            return ResponseEntity.ok().body(
                    new GenericResponseForBody<>(
                            List.of("SUCCESSFUL"),
                            productDtoResult.getValue()
                    )

            );
        }
    }

}
