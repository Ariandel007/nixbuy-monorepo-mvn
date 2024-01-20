package com.mvnnixbuyapi.paymentservice.services.impl;

import com.mvnnixbuyapi.commons.dtos.response.GenericResponseForBody;
import com.mvnnixbuyapi.paymentservice.clients.feign.ProductsFeign;
import com.mvnnixbuyapi.paymentservice.dto.reponse.ProductDto;
import com.mvnnixbuyapi.paymentservice.services.StripeProductsService;
import com.mvnnixbuyapi.paymentservice.utils.UtilStripeApp;
import com.stripe.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class StripeProductsServiceImpl implements StripeProductsService {

    private final ProductsFeign productsFeign;

    @Autowired
    public StripeProductsServiceImpl(ProductsFeign productsFeign) {
        this.productsFeign = productsFeign;
    }

    @Override
    public List<Product> getProductsById(List<Long> ids) {
        ResponseEntity<List<GenericResponseForBody<ProductDto>>> listResponseEntity = this.productsFeign.listResponseEntityProductDto(ids);
        List<GenericResponseForBody<ProductDto>> productResponse = listResponseEntity.getBody();

        List<Product> productList = productResponse.stream().map(productResponseItem -> UtilStripeApp.createProduct(
                productResponseItem.getData().getName(),
                productResponseItem.getData().getId(),
                "USD",
                productResponseItem.getData().getPrice()
                )
        ).toList();

        return productList;
    }
}
