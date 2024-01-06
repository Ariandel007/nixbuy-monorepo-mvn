package com.mvnnixbuyapi.keyProduct.adapters.jpa.repository;

import com.mvnnixbuyapi.keyProduct.adapters.entity.KeyProductEntity;
import com.mvnnixbuyapi.keyProduct.adapters.jpa.KeySpringJpaAdapterRepository;
import com.mvnnixbuyapi.keyProduct.adapters.mapper.KeyDboMapper;
import com.mvnnixbuyapi.keyProduct.model.entity.KeyProduct;
import com.mvnnixbuyapi.keyProduct.port.repository.KeyRepository;
import com.mvnnixbuyapi.product.adapter.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = false)
public class KeyPostgresRepository implements KeyRepository {

    private final KeySpringJpaAdapterRepository keySpringJpaAdapterRepository;
    private final KeyDboMapper keyDboMapper;

    @Autowired
    public KeyPostgresRepository(
            KeySpringJpaAdapterRepository keySpringJpaAdapterRepository,
            KeyDboMapper keyDboMapper
    ) {
        this.keySpringJpaAdapterRepository = keySpringJpaAdapterRepository;
        this.keyDboMapper = keyDboMapper;
    }

    @Override
    public KeyProduct create(KeyProduct keyProduct) {
        var keyEntityToCreate = this.keyDboMapper.domainToEntity(keyProduct);
        var keyEntityCreated = this.keySpringJpaAdapterRepository.save(keyEntityToCreate);
        return this.keyDboMapper.entityToDomain(keyEntityCreated);
    }

    @Override
    public KeyProduct edit(KeyProduct keyProduct) {
        var keyEntityToCreate = this.keyDboMapper.domainToEntity(keyProduct);
        var keyEntityCreated = this.keySpringJpaAdapterRepository.save(keyEntityToCreate);
        return this.keyDboMapper.entityToDomain(keyEntityCreated);
    }

    @Override
    @Transactional(readOnly = true)
    public KeyProduct find(KeyProduct keyProduct) {
        Optional<KeyProductEntity> optionalKeyProductEntity = this.keySpringJpaAdapterRepository.findById(keyProduct.getId());
        if(optionalKeyProductEntity.isPresent()) {
            return this.keyDboMapper.entityToDomain(optionalKeyProductEntity.get());
        }
        return null;    }
}
