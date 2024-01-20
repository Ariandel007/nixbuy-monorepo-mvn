package com.mvnnixbuyapi.product.rest.controller;

import com.mvnnixbuyapi.commons.dtos.response.GenericResponseForBody;
import com.mvnnixbuyapi.product.model.dto.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/query-product-endpoint")
public class ProductQueryController {

    @GetMapping(value = "/api/query-product-endpoint/v1/get-products")
    public ResponseEntity<List<GenericResponseForBody<ProductDto>>> listResponseEntityProductDto(
            @RequestParam("ids") List<Long> ids
    ) {
        // Devolver la lista de respuestas envuelta en ResponseEntity
        return ResponseEntity.ok(responses);
    }

}
