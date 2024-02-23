package com.mvnnixbuyapi.paymentservice.services.impl;

import com.mvnnixbuyapi.commons.dtos.response.GenericResponseForBody;
import com.mvnnixbuyapi.paymentservice.clients.feign.ProductsFeign;
import com.mvnnixbuyapi.paymentservice.dto.reponse.ProductDto;
import com.mvnnixbuyapi.paymentservice.services.StripeProductsService;
import com.mvnnixbuyapi.paymentservice.utils.UtilStripeApp;
import com.stripe.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class StripeProductsServiceImpl implements StripeProductsService {

    private final ProductsFeign productsFeign;
    private CircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    public StripeProductsServiceImpl(
            ProductsFeign productsFeign,
            CircuitBreakerFactory circuitBreakerFactory
    ) {
        this.productsFeign = productsFeign;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    @Override
    public List<Product> getProductsById(Long orderId) {
// Sin Circuit Breaker
//        ResponseEntity<GenericResponseForBody<List<ProductDto>>> listResponseEntity =
//                this.productsFeign.listResponseEntityProductDto(orderId);
//        GenericResponseForBody<List<ProductDto>> productResponse = listResponseEntity.getBody();

        // Aplicando circuit breaker:
        GenericResponseForBody<List<ProductDto>> productResponse = null;
        try{
            productResponse = this.getProductResponseWithCircuitBreaker(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error message {}", e.getMessage());
            // TODO: CHANGE EXCEPTION
            throw new RuntimeException("ERROR");
        }

        List<Product> productList = productResponse.getData().stream().map(productResponseItem -> UtilStripeApp.createProduct(
                productResponseItem.getName(),
                productResponseItem.getId(),
                "USD",
                productResponseItem.getPrice().multiply(new BigDecimal(productResponseItem.getQuantity()))
                )
        ).toList();

        return productList;
    }

    private GenericResponseForBody<List<ProductDto>> getProductResponseWithCircuitBreaker(long orderId) {
        return this.circuitBreakerFactory.create("listResponseEntityProductDtoCircuitBreaker")
                .run(()->this.getProductResponse(orderId)
//                ,e->new GenericResponseForBody<>().setData("")// Camino alterno cuando el circuito este abierto
                );
    }

    private GenericResponseForBody<List<ProductDto>> getProductResponse(long orderId){
        ResponseEntity<GenericResponseForBody<List<ProductDto>>> listResponseEntity =
                this.productsFeign.listResponseEntityProductDto(orderId);
        GenericResponseForBody<List<ProductDto>> productResponse = listResponseEntity.getBody();
        log.info("productResponse: {}", productResponse);
        return productResponse;
    }

}
