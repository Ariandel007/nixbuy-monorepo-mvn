package com.mvnnixbuyapi.keyproduct.command;

import com.mvnnixbuyapi.commons.monads.ResultMonad;
import com.mvnnixbuyapi.keyProduct.model.dto.KeyToCreateDto;
import com.mvnnixbuyapi.keyProduct.model.dto.command.KeyCreateCommand;
import com.mvnnixbuyapi.keyProduct.service.KeyCreateService;
import com.mvnnixbuyapi.product.mapper.ProductDtoMapper;
import com.mvnnixbuyapi.product.model.dto.ProductDto;
import com.mvnnixbuyapi.product.model.dto.command.ProductCreateCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KeyCreateHandler {
    private final KeyCreateService keyCreateService;

    @Autowired
    public KeyCreateHandler(
            KeyCreateService keyCreateService
    ) {
        this.keyCreateService = keyCreateService;
    }

    public ResultMonad<KeyToCreateDto> execute(KeyCreateCommand keyCreateCommand) {
        return null;
    }
}
