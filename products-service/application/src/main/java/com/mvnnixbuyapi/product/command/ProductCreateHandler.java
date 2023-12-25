package com.mvnnixbuyapi.product.command;

import com.mvnnixbuyapi.commons.monads.ResultMonad;
import com.mvnnixbuyapi.product.mapper.ProductDtoMapper;
import com.mvnnixbuyapi.product.model.dto.ProductDto;
import com.mvnnixbuyapi.product.model.dto.command.ProductCreateCommand;
import com.mvnnixbuyapi.product.service.ProductCreateService;
import org.springframework.stereotype.Component;

@Component
public class ProductCreateHandler {
    private final ProductCreateService productCreateService;
    private final ProductDtoMapper productDtoMapper;

    public ProductCreateHandler(
            ProductCreateService productCreateService,
            ProductDtoMapper productDtoMapper
    ){
        this.productCreateService = productCreateService;
        this.productDtoMapper = productDtoMapper;
    }

    public ResultMonad<ProductDto> execute(ProductCreateCommand productCreateCommand) {
        ProductDto productDtoCreated = this.productDtoMapper.toDto(this.productCreateService.execute(productCreateCommand));
        return ResultMonad.ok(productDtoCreated);
    }



}
