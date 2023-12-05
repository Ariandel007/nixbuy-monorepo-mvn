package com.mvnnixbuyapi.userservice.exceptions;

import com.mvnnixbuyapi.commons.exceptions.GeneralException;

public class ErrorUploadToCloudinary extends GeneralException {
    public ErrorUploadToCloudinary(String errorCode, String message) {
        super(errorCode, message);
    }
}
