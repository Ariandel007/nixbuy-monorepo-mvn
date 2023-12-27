package com.mvnnixbuyapi.product.command;

import com.mvnnixbuyapi.commons.monads.ResultMonad;
import com.mvnnixbuyapi.product.mapper.ProductDtoMapper;
import com.mvnnixbuyapi.product.model.dto.ProductToEditDto;
import com.mvnnixbuyapi.product.model.dto.command.ProductEditCommand;
import com.mvnnixbuyapi.product.service.ProductEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductEditHandler {
    private final ProductEditService productEditService;

    @Autowired
    public ProductEditHandler(ProductEditService productEditService) {
        this.productEditService = productEditService;
    }

    public ResultMonad<ProductToEditDto> execute(ProductEditCommand productEditCommand) {
        if(productEditCommand.getIsPhotoUploaded()){
            // subir foto a un proveedor externo y setear Link
        }
        ProductToEditDto productToEditDto =
                ProductDtoMapper.INSTANCE.toProductEditDto(this.productEditService.execute(productEditCommand));

        return ResultMonad.ok(productToEditDto);
    }

}
