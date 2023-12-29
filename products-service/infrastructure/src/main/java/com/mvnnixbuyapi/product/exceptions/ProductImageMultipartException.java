package com.mvnnixbuyapi.product.exceptions;

import com.mvnnixbuyapi.commons.exceptions.GeneralException;

public class ProductImageMultipartException extends GeneralException {
    public ProductImageMultipartException(String errorCode) {
        super(errorCode);
    }
}
