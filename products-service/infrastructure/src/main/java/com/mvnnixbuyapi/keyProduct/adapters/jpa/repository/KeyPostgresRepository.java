package com.mvnnixbuyapi.keyProduct.adapters.jpa.repository;

import com.mvnnixbuyapi.keyProduct.adapters.entity.KeyProductEntity;
import com.mvnnixbuyapi.keyProduct.adapters.jpa.KeySpringJpaAdapterRepository;
import com.mvnnixbuyapi.keyProduct.adapters.mapper.KeyDboMapper;
import com.mvnnixbuyapi.keyProduct.model.entity.KeyProduct;
import com.mvnnixbuyapi.keyProduct.port.repository.KeyRepository;
import com.mvnnixbuyapi.product.model.dto.ProductReceivedDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
        return null;
    }

    @Override
    public void setOrderIds(List<KeyProduct> products) {
        List<Long> idKeyProducts = products.stream().map(KeyProduct::getId).toList();
        int quantityExpected = products.size();

        int quantityUpdated = this.keySpringJpaAdapterRepository.updateOrderIdFieldOnKeyProducts(
                idKeyProducts,
                products.get(0).getKeyOrder().order().getId()
        );
        if (quantityExpected != quantityUpdated ) {
            //TODO: MAKE AN GRAFEFUL EXCEPTION
            throw new IllegalStateException("Error: Not all products were updated. Performing rollback.");
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<KeyProduct> findByProductsId(List<ProductReceivedDto> productList) {
        List<KeyProductEntity> result = new ArrayList<>();
        for(ProductReceivedDto productReceivedDto : productList){
            List<KeyProductEntity> keyProducts = this.keySpringJpaAdapterRepository.findLimitedByProductIdAndQuantity(
                    productReceivedDto.getId(),
                    productReceivedDto.getQuantity());
            result.addAll(keyProducts);
        }
        List<KeyProduct> keyProductList = result.stream().map(this.keyDboMapper::entityToDomain).toList();
        return keyProductList;
    }
}
