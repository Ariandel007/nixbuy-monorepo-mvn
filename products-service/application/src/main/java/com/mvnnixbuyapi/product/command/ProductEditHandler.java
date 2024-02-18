package com.mvnnixbuyapi.product.command;

import com.mvnnixbuyapi.commons.monads.ResultMonad;
import com.mvnnixbuyapi.product.mapper.ProductDtoMapper;
import com.mvnnixbuyapi.product.model.dto.ProductToEditDto;
import com.mvnnixbuyapi.product.model.dto.command.ProductEditCommand;
import com.mvnnixbuyapi.product.model.entity.Product;
import com.mvnnixbuyapi.product.service.ProductEditService;
import com.mvnnixbuyapi.product.service.ProductUpdatePhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductEditHandler {
    private final ProductEditService productEditService;
    private final ProductUpdatePhotoService productUpdatePhotoService;

    @Autowired
    public ProductEditHandler(
            ProductEditService productEditService,
            ProductUpdatePhotoService productUpdatePhotoService
    ) {
        this.productEditService = productEditService;
        this.productUpdatePhotoService = productUpdatePhotoService;
    }

    @Transactional
    public ResultMonad<ProductToEditDto> execute(ProductEditCommand productEditCommand) {
        if(productEditCommand.getIsPhotoUploaded()){
            if(productEditCommand.getMainPhotoOfProductFile() == null) {
                return ResultMonad.error("NULL_MULTIPART_PHOTO_PRODUCT_ERROR");
            }
            // Subir foto a un proveedor externo y setear Link
            String urlImage = this.productUpdatePhotoService.execute(productEditCommand);
            productEditCommand.setUrlImage(urlImage);
        }

        ResultMonad<Product> productResultMonad = this.productEditService.execute(productEditCommand);

        if(productResultMonad.isError()){
            return ResultMonad.error(productResultMonad.getError());
        }

        return ResultMonad.ok(ProductDtoMapper.INSTANCE.toProductEditDto(productResultMonad.getValue()));
    }

}
