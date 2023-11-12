package com.mvnnixbuyapi.apigatewayservice.exceptions;

import com.mvnnixbuyapi.commons.exceptions.GeneralException;

public class JwtTokenMalformedException extends GeneralException {

    public JwtTokenMalformedException(String errorCode, String message) {
        super(errorCode, message);
    }
}
