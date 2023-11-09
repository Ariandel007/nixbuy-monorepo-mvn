package com.mvnnixbuyapi.gatewayservice.userservice.exceptions;

import com.mvnnixbuyapi.gatewayservice.commons.exceptions.GeneralException;

public class UserAlreadyExistsException  extends GeneralException {
    public UserAlreadyExistsException(String errorCode, String message) {
        super(errorCode,message);
    }
}