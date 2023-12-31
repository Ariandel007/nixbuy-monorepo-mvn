package com.mvnnixbuyapi.product.rest.controller;

import com.mvnnixbuyapi.commons.dtos.response.GenericResponseForBody;
import com.mvnnixbuyapi.commons.monads.ResultMonad;
import com.mvnnixbuyapi.commons.utils.ResponseUtils;
import com.mvnnixbuyapi.product.command.ProductCreateHandler;
import com.mvnnixbuyapi.product.command.ProductEditHandler;
import com.mvnnixbuyapi.product.model.dto.ProductDto;
import com.mvnnixbuyapi.product.model.dto.ProductToEditDto;
import com.mvnnixbuyapi.product.model.dto.command.ProductCreateCommand;
import com.mvnnixbuyapi.product.model.dto.command.ProductEditCommand;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/command-product-endpoint")
public class ProductCommandController {
    private final ProductCreateHandler productCreateHandler;
    private final ProductEditHandler productEditHandler;


    @Autowired
    public ProductCommandController(
            ProductCreateHandler productCreateHandler,
            ProductEditHandler productEditHandler
    ) {
        this.productCreateHandler = productCreateHandler;
        this.productEditHandler = productEditHandler;
    }

    @PostMapping("/v1/create-product")
    public ResponseEntity<GenericResponseForBody<ProductDto>> createProductV1(
            @RequestBody @Valid ProductCreateCommand productCreateCommand,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()) {
            return ResponseUtils.buildBadRequestResponse(bindingResult);
        }

        ResultMonad<ProductDto> productDtoResult = this.productCreateHandler.execute(productCreateCommand);

        if(productDtoResult.isError()){
            return ResponseUtils.buildBadRequestResponse(productDtoResult.getError());
        } else {
            return ResponseUtils.buildSuccessResponse(productDtoResult.getValue());
//            return ResponseEntity.ok().body(
//                    new GenericResponseForBody<>(
//                            List.of("SUCCESSFUL"),
//                            productDtoResult.getValue()
//                    )
//
//            );
        }
    }

    @PatchMapping(value = "/v1/update-main-photo/{productId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<GenericResponseForBody<ProductToEditDto>> editProductV1(
            @PathVariable Long productId,
            @RequestPart(value = "mainPhotoOfProductFile") MultipartFile mainPhotoOfProductFile,
            @RequestPart(value = "productName") String productName,
            @RequestPart(value = "productDescription") String productDescription,
            @RequestPart(value = "urlImage") String urlImage,
            @RequestPart(value = "isPhotoUploaded") String isPhotoUploaded,
            @RequestPart(value = "price") String price


    ) {
        var productEditCommand = ProductEditCommand.builder()
                .id(productId)
                .name(productName)
                .description(productDescription)
                .urlImage(urlImage)
                .isPhotoUploaded(isPhotoUploaded == null ? null:isPhotoUploaded.equals("true"))
                .mainPhotoOfProductFile(mainPhotoOfProductFile)
                .price(new BigDecimal(price))
                .build();
        BindingResult bindingResult = new BeanPropertyBindingResult(productEditCommand, "productEditCommand");

        if(bindingResult.hasErrors()) {
            return ResponseUtils.buildBadRequestResponse(bindingResult);
        }

        ResultMonad<ProductToEditDto> productToEditDtoResultMonad = this.productEditHandler.execute(productEditCommand);

        if(productToEditDtoResultMonad.isError()){
            return ResponseUtils.buildBadRequestResponse(productToEditDtoResultMonad.getError());
        } else {
            return ResponseUtils.buildSuccessResponse(productToEditDtoResultMonad.getValue());
        }
    }

}
