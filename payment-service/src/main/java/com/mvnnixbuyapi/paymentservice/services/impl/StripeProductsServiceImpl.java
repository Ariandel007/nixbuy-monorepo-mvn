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
        ResponseEntity<GenericResponseForBody<List<ProductDto>>> listResponseEntity = this.productsFeign.listResponseEntityProductDto(ids);
        GenericResponseForBody<List<ProductDto>> productResponse = listResponseEntity.getBody();

        List<Product> productList = productResponse.getData().stream().map(productResponseItem -> UtilStripeApp.createProduct(
                productResponseItem.getName(),
                productResponseItem.getId(),
                "USD",
                productResponseItem.getPrice()
                )
        ).toList();

        return productList;
    }
}
