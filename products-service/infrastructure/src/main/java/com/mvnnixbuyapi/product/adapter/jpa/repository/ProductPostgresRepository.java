package com.mvnnixbuyapi.product.adapter.jpa.repository;

import com.mvnnixbuyapi.product.adapter.jpa.ProductSpringJpaAdapterRepository;
import com.mvnnixbuyapi.product.adapter.mapper.ProductDboMapper;
import com.mvnnixbuyapi.product.model.entity.Product;
import com.mvnnixbuyapi.product.port.repository.ProductRepository;

public class ProductPostgresRepository implements ProductRepository {

    private final ProductSpringJpaAdapterRepository productSpringJpaAdapterRepository;
    private final ProductDboMapper productDboMapper;

    public ProductPostgresRepository(
            ProductSpringJpaAdapterRepository productSpringJpaAdapterRepository,
            ProductDboMapper productDboMapper
    ){
        this.productSpringJpaAdapterRepository = productSpringJpaAdapterRepository;
        this.productDboMapper = productDboMapper;
    }
    @Override
    public Product create(Product product) {
        var productEntity = this.productDboMapper.domainToEntity(product);
        var productCreated = this.productSpringJpaAdapterRepository.save(productEntity);
        return this.productDboMapper.entityToProduct(productCreated);
    }
}
