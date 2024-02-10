package com.mvnnixbuyapi.product.rest.controller;

import com.mvnnixbuyapi.commons.dtos.response.GenericResponseForBody;
import com.mvnnixbuyapi.commons.utils.ResponseUtils;
import com.mvnnixbuyapi.product.model.dto.ProductDto;
import com.mvnnixbuyapi.product.model.dto.query.ItemCartDto;
import com.mvnnixbuyapi.product.query.ProductListByOrderIdHandler;
import com.mvnnixbuyapi.product.query.ProductsAvaibleToAddToOrderHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/query-product-endpoint")
public class ProductQueryController {
    private final ProductListByOrderIdHandler productListByOrderIdHandler;
    private final ProductsAvaibleToAddToOrderHandler productsAvaibleToAddToOrderHandler;

    @Autowired
    public ProductQueryController(
            ProductListByOrderIdHandler productListByOrderIdHandler,
            ProductsAvaibleToAddToOrderHandler productsAvaibleToAddToOrderHandler
    ) {
        this.productListByOrderIdHandler = productListByOrderIdHandler;
        this.productsAvaibleToAddToOrderHandler = productsAvaibleToAddToOrderHandler;
    }

    @GetMapping(value = "/v1/get-products-of-orderId")
    public ResponseEntity<GenericResponseForBody<List<ProductDto>>> listResponseEntityProductDtoToBuy(
            @RequestParam("orderId") Long orderId
    ) {
        List<ProductDto> productDtoList = this.productListByOrderIdHandler.execute(orderId);
        return ResponseUtils.buildSuccessResponse(productDtoList);
    }

    @PostMapping(value = "/v1/products-to-add-to-order")
    ResponseEntity<GenericResponseForBody<List<ProductDto>>> listResponseEntityProductDtoToAddToOrder(
            @RequestBody List<ItemCartDto> itemCartDtoList
    ) {
        List<ProductDto> productDtoList = this.productsAvaibleToAddToOrderHandler.execute(itemCartDtoList);
        return ResponseUtils.buildSuccessResponse(productDtoList);
    }

}
