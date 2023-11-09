package com.mvnnixbuyapi.gatewayservice.userservice.exceptions;

import com.mvnnixbuyapi.gatewayservice.commons.exceptions.GeneralException;

public class InvalidRangeOfPasswordException extends GeneralException {
    public InvalidRangeOfPasswordException(String errorCode, String message) {
        super(errorCode, message);
    }
}
