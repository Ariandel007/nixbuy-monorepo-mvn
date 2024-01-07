package com.mvnnixbuyapi.keyproduct.command;

import com.mvnnixbuyapi.commons.monads.ResultMonad;
import com.mvnnixbuyapi.keyProduct.model.dto.KeyToCreateDto;
import com.mvnnixbuyapi.keyProduct.model.dto.command.KeyCreateCommand;
import com.mvnnixbuyapi.keyProduct.service.KeyCreateService;
import com.mvnnixbuyapi.keyproduct.mapper.KeyDtoMapper;
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
        KeyToCreateDto keyDtoCreated = KeyDtoMapper.INSTANCE.toDtoCreate(keyCreateService.execute(keyCreateCommand));
        return ResultMonad.ok(keyDtoCreated);
    }
}
