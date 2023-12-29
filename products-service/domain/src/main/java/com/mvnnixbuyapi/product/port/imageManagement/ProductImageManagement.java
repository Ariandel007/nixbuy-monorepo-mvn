package com.mvnnixbuyapi.product.port.imageManagement;

import com.mvnnixbuyapi.product.model.entity.Product;
import org.springframework.web.multipart.MultipartFile;

public interface ProductImageManagement {
    String generateNewUrl(Product product, MultipartFile file);
}
