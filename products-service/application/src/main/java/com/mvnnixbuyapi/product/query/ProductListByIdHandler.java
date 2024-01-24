package com.mvnnixbuyapi.product.query;

import com.mvnnixbuyapi.product.mapper.ProductDtoMapper;
import com.mvnnixbuyapi.product.model.dto.ProductDto;
import com.mvnnixbuyapi.product.port.dao.ProductDao;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductListByIdHandler {
    private final ProductDao productDao;

    public ProductListByIdHandler(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductDto> execute(List<Long> productIds, Long idPlatform) {
        return this.productDao.getProductByIds(productIds, idPlatform);
    }

}
