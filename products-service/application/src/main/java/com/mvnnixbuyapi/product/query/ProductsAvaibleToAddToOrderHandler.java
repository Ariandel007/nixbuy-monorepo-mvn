package com.mvnnixbuyapi.product.query;

import com.mvnnixbuyapi.product.model.dto.ProductDto;
import com.mvnnixbuyapi.product.model.dto.query.ItemCartDto;
import com.mvnnixbuyapi.product.port.dao.ProductDao;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class ProductsAvaibleToAddToOrderHandler {
    private final ProductDao productDao;

    public ProductsAvaibleToAddToOrderHandler(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductDto> execute(List<ItemCartDto> itemCartDtoList) {
        itemCartDtoList.sort(Comparator.comparing(ItemCartDto::getProductId));
        var productsAvailable = this.productDao.getProductsAvailable(itemCartDtoList);
        productsAvailable.sort(Comparator.comparing(ProductDto::getId));

        if (productsAvailable.size() != itemCartDtoList.size()){
            return List.of();
        }

        for (int i = 0; i < productsAvailable.size(); i++) {
            var productAvailable = productsAvailable.get(i);
            var itemFromList = itemCartDtoList.get(i);
            if(productAvailable.getQuantity() < itemFromList.getQuantity()) {
                return List.of();
            } else {
                productAvailable.setQuantity(itemFromList.getQuantity());
            }
        }

        return productsAvailable;
    }

}
