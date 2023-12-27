package com.mvnnixbuyapi.product.command;

import com.mvnnixbuyapi.commons.monads.ResultMonad;
import com.mvnnixbuyapi.product.mapper.ProductDtoMapper;
import com.mvnnixbuyapi.product.model.dto.ProductDto;
import com.mvnnixbuyapi.product.model.dto.command.ProductCreateCommand;
import com.mvnnixbuyapi.product.service.ProductCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductCreateHandler {
    private final ProductCreateService productCreateService;

    @Autowired
    public ProductCreateHandler(
            ProductCreateService productCreateService
    ){
        this.productCreateService = productCreateService;
    }

    public ResultMonad<ProductDto> execute(ProductCreateCommand productCreateCommand) {
        ProductDto productDtoCreated =
                ProductDtoMapper.INSTANCE.toDto(this.productCreateService.execute(productCreateCommand));
        return ResultMonad.ok(productDtoCreated);
    }



}
