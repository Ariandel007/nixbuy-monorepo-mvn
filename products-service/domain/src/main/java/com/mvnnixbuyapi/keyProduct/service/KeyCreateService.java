package com.mvnnixbuyapi.keyProduct.service;

import com.mvnnixbuyapi.keyProduct.model.dto.command.KeyCreateCommand;
import com.mvnnixbuyapi.keyProduct.model.entity.KeyProduct;
import com.mvnnixbuyapi.keyProduct.port.repository.KeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KeyCreateService {
    private final KeyRepository  keyRepository;

    @Autowired
    public KeyCreateService(KeyRepository keyRepository) {
        this.keyRepository = keyRepository;
    }

    public KeyProduct execute(KeyCreateCommand keyCreateCommand) {
        var keyToCreate = new KeyProduct().requestToCreate(keyCreateCommand);
        return this.keyRepository.create(keyToCreate);
    }
}
