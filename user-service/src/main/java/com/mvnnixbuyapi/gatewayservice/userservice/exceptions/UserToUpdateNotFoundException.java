package com.mvnnixbuyapi.gatewayservice.userservice.exceptions;

import com.mvnnixbuyapi.gatewayservice.commons.exceptions.GeneralException;

public class UserToUpdateNotFoundException extends GeneralException {
    public UserToUpdateNotFoundException(String errorCode, String message) {
        super(errorCode,message);
    }
}
