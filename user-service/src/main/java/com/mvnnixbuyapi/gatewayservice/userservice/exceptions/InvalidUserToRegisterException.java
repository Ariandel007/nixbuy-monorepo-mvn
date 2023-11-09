package com.mvnnixbuyapi.gatewayservice.userservice.exceptions;

import com.mvnnixbuyapi.gatewayservice.commons.exceptions.GeneralException;

public class InvalidUserToRegisterException extends GeneralException {
    public InvalidUserToRegisterException(String errorCode, String message) {
        super(errorCode,message);
    }
}
