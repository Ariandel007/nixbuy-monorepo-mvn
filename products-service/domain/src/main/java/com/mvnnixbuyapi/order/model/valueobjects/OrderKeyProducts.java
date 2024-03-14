package com.mvnnixbuyapi.order.model.valueobjects;

import com.mvnnixbuyapi.keyProduct.model.entity.KeyProduct;

import java.util.List;

public record OrderKeyProducts(List<KeyProduct> keyProductList) {
}
