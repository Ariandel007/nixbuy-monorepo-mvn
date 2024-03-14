package com.mvnnixbuyapi.product.adapter.jpa.repository;

import com.mvnnixbuyapi.product.adapter.entity.ProductEntity;
import com.mvnnixbuyapi.product.adapter.jpa.ProductSpringJpaAdapterRepository;
import com.mvnnixbuyapi.product.adapter.mapper.ProductDboMapper;
import com.mvnnixbuyapi.product.model.entity.Product;
import com.mvnnixbuyapi.product.port.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = false)
public class ProductPostgresRepository implements ProductRepository {

    private final ProductSpringJpaAdapterRepository productSpringJpaAdapterRepository;
    private final ProductDboMapper productDboMapper;

    @Autowired
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

    @Override
    public Product edit(Product product) {
        var productEntity = this.productDboMapper.domainToEntity(product);
        var productUpdated= this.productSpringJpaAdapterRepository.save(productEntity);
        return this.productDboMapper.entityToProduct(productUpdated);
    }

    @Override
    @Transactional(readOnly = true)
    public Product find(Product product) {
        Optional<ProductEntity> optionalProductEntity = this.productSpringJpaAdapterRepository.findById(product.getId());
        if(optionalProductEntity.isPresent()) {
            return this.productDboMapper.entityToProduct(optionalProductEntity.get());
        }
        return null;
    }

    @Override
    public List<Product> findProductsById(List<Long> productIdList) {
        return null;
    }
}
