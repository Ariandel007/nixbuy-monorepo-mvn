package com.mvnnixbuyapi.userservice.exceptions;

import com.mvnnixbuyapi.commons.exceptions.GeneralException;

public class UserAppplicationNotFoundException extends GeneralException {
    public UserAppplicationNotFoundException(String errorCode, String message) {
        super(errorCode, message);
    }

    public UserAppplicationNotFoundException(String errorCode) {
        super(errorCode);
    }
}
