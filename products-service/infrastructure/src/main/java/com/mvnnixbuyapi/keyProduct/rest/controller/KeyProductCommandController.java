package com.mvnnixbuyapi.keyProduct.rest.controller;

import com.mvnnixbuyapi.commons.dtos.response.GenericResponseForBody;
import com.mvnnixbuyapi.keyProduct.model.dto.KeyToCreateDto;
import com.mvnnixbuyapi.keyProduct.model.dto.command.KeyCreateCommand;
import com.mvnnixbuyapi.keyproduct.command.KeyCreateHandler;
import com.mvnnixbuyapi.product.model.dto.ProductDto;
import com.mvnnixbuyapi.product.model.dto.command.ProductCreateCommand;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/command-key-endpoint")
public class KeyProductCommandController {
    private final KeyCreateHandler keyCreateHandler;

    @Autowired
    public KeyProductCommandController(
            KeyCreateHandler keyCreateHandler
    ) {
        this.keyCreateHandler = keyCreateHandler;
    }

    @PostMapping("/v1/create-key")
    public ResponseEntity<GenericResponseForBody<KeyToCreateDto>> createProductV1(
            @RequestBody @Valid KeyCreateCommand keyCreateCommand,
            BindingResult bindingResult
    ){
        return null;
    }
}
