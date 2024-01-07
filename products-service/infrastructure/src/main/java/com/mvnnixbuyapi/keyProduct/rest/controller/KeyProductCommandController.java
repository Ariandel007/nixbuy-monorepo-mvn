package com.mvnnixbuyapi.keyProduct.rest.controller;

import com.mvnnixbuyapi.commons.dtos.response.GenericResponseForBody;
import com.mvnnixbuyapi.commons.monads.ResultMonad;
import com.mvnnixbuyapi.commons.utils.ResponseUtils;
import com.mvnnixbuyapi.keyProduct.model.dto.KeyToCreateDto;
import com.mvnnixbuyapi.keyProduct.model.dto.command.KeyCreateCommand;
import com.mvnnixbuyapi.keyproduct.command.KeyCreateHandler;
import com.mvnnixbuyapi.product.model.dto.ProductDto;
import com.mvnnixbuyapi.product.model.dto.command.ProductCreateCommand;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<GenericResponseForBody<KeyToCreateDto>> createKeyV1(
            @RequestBody @Valid KeyCreateCommand keyCreateCommand,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()) {
            return ResponseUtils.buildBadRequestResponse(bindingResult);
        }
        ResultMonad<KeyToCreateDto> keyCreateDtoResultMonad = this.keyCreateHandler.execute(keyCreateCommand);

        if(keyCreateDtoResultMonad.isError()){
            return ResponseUtils.buildBadRequestResponse(keyCreateDtoResultMonad.getError());
        } else{
            return ResponseUtils.buildSuccessResponse(keyCreateDtoResultMonad.getValue());
        }
    }
}
