package com.mvnnixbuyapi.userservice.exceptions;

import com.mvnnixbuyapi.commons.exceptions.GeneralException;

public class InvalidUserToRegisterException extends GeneralException {
    public InvalidUserToRegisterException(String errorCode, String message) {
        super(errorCode,message);
    }
}
