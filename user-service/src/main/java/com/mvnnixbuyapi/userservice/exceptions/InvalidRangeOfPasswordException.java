package com.mvnnixbuyapi.userservice.exceptions;

import com.mvnnixbuyapi.commons.exceptions.GeneralException;

public class InvalidRangeOfPasswordException extends GeneralException {
    public InvalidRangeOfPasswordException(String errorCode, String message) {
        super(errorCode, message);
    }
}
