package com.mvnnixbuyapi.paymentservice.clients.feign;

import com.mvnnixbuyapi.commons.dtos.response.GenericResponseForBody;
import com.mvnnixbuyapi.paymentservice.dto.reponse.ProductDto;
import com.mvnnixbuyapi.paymentservice.dto.request.ItemCartDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "products-service-nixbuy")
public interface ProductsFeign {
    @GetMapping(value = "/api/query-product-endpoint/v1/get-products-of-orderId", produces = "application/json")
    ResponseEntity<GenericResponseForBody<List<ProductDto>>> listResponseEntityProductDto(
            @RequestParam("orderId") Long orderId
    );

    @PostMapping(value = "/api/query-product-endpoint/v1/products-to-add-to-order", produces = "application/json")
    ResponseEntity<GenericResponseForBody<List<ProductDto>>> listResponseEntityProductDtoToAddToOrder(
            @RequestBody List<ItemCartDto> itemCartDtoList
    );

}
