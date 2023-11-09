package com.mvnnixbuyapi.gatewayservice.exceptions;

import com.mvnnixbuyapi.gatewayservice.commons.exceptions.GeneralException;

public class JwtTokenMissingException extends GeneralException {
    public JwtTokenMissingException(String errorCode, String message) {
        super(errorCode, message);
    }
}
