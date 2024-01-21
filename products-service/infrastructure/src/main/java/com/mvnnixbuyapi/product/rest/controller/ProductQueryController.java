package com.mvnnixbuyapi.product.rest.controller;

import com.mvnnixbuyapi.commons.dtos.response.GenericResponseForBody;
import com.mvnnixbuyapi.commons.utils.ResponseUtils;
import com.mvnnixbuyapi.product.model.dto.ProductDto;
import com.mvnnixbuyapi.product.query.ProductListByIdHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/query-product-endpoint")
public class ProductQueryController {
    private final ProductListByIdHandler productListByIdHandler;

    @Autowired
    public ProductQueryController(ProductListByIdHandler productListByIdHandler) {
        this.productListByIdHandler = productListByIdHandler;
    }

    @GetMapping(value = "/v1/get-products-to-buy")
    public ResponseEntity<GenericResponseForBody<List<ProductDto>>> listResponseEntityProductDtoToBuy(
            @RequestParam("ids") List<Long> ids
    ) {
        if(ids.size() > 20) {
            return ResponseUtils.buildBadRequestResponse("TOO_LONG_LIST_ERROR");
        }

        List<ProductDto> productDtoList = this.productListByIdHandler.execute(ids);
        return ResponseUtils.buildSuccessResponse(productDtoList);
    }

}
