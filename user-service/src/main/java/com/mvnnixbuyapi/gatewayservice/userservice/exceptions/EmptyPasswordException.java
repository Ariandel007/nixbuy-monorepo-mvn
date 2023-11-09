package com.mvnnixbuyapi.gatewayservice.userservice.exceptions;

import com.mvnnixbuyapi.gatewayservice.commons.exceptions.GeneralException;

public class EmptyPasswordException extends GeneralException {
    public EmptyPasswordException(String errorCode, String message) {
        super(errorCode, message);
    }
}
