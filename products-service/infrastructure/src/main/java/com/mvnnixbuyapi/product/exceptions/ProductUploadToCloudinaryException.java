package com.mvnnixbuyapi.product.exceptions;

import com.mvnnixbuyapi.commons.exceptions.GeneralException;

public class ProductUploadToCloudinaryException extends GeneralException {
    public ProductUploadToCloudinaryException(String errorCode) {
        super(errorCode);
    }
}
