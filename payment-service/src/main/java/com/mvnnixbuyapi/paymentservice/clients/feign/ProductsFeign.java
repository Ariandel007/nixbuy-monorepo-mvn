package com.mvnnixbuyapi.paymentservice.clients.feign;

import com.mvnnixbuyapi.commons.dtos.response.GenericResponseForBody;
import com.mvnnixbuyapi.paymentservice.dto.reponse.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "products-service-nixbuy")
public interface ProductsFeign {
    @GetMapping(value = "/api/query-product-endpoint/v1/get-products", produces = "application/json")
    ResponseEntity<List<GenericResponseForBody<ProductDto>>> listResponseEntityProductDto(
            @RequestParam("ids") List<Long> ids
    );

}
