package com.mvnnixbuyapi.userservice.exceptions;

import com.mvnnixbuyapi.commons.exceptions.GeneralException;

public class UserToUpdateNotFoundException extends GeneralException {
    public UserToUpdateNotFoundException(String errorCode, String message) {
        super(errorCode,message);
    }
}
