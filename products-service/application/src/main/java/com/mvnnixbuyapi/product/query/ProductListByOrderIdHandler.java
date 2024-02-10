package com.mvnnixbuyapi.product.query;

import com.mvnnixbuyapi.product.model.dto.ProductDto;
import com.mvnnixbuyapi.product.port.dao.ProductDao;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductListByOrderIdHandler {
    private final ProductDao productDao;

    public ProductListByOrderIdHandler(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductDto> execute(Long orderId) {
        return this.productDao.getProductByIds(orderId);
    }

}
