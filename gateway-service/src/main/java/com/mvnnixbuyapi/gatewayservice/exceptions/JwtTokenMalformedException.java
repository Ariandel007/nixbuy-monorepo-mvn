package com.mvnnixbuyapi.gatewayservice.exceptions;

import com.mvnnixbuyapi.gatewayservice.commons.exceptions.GeneralException;

public class JwtTokenMalformedException extends GeneralException {

    public JwtTokenMalformedException(String errorCode, String message) {
        super(errorCode, message);
    }
}
