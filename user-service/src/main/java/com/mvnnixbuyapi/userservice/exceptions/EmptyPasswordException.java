package com.mvnnixbuyapi.userservice.exceptions;

import com.mvnnixbuyapi.commons.exceptions.GeneralException;

public class EmptyPasswordException extends GeneralException {
    public EmptyPasswordException(String errorCode, String message) {
        super(errorCode, message);
    }
}
