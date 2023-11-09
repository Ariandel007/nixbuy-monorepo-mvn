package com.mvnnixbuyapi.gatewayservice.userservice.exceptions;

import com.mvnnixbuyapi.gatewayservice.commons.exceptions.GeneralException;

public class InvalidPatternOfPasswordException extends GeneralException {
    public InvalidPatternOfPasswordException(String errorCode, String message) {
        super(errorCode, message);
    }
}
