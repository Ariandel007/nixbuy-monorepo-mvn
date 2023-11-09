package com.mvnnixbuyapi.userservice.exceptions;

import com.mvnnixbuyapi.commons.exceptions.GeneralException;

public class UserAlreadyExistsException  extends GeneralException {
    public UserAlreadyExistsException(String errorCode, String message) {
        super(errorCode,message);
    }
}