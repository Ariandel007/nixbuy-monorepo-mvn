package com.mvnnixbuyapi.userservice.exceptions;

import com.mvnnixbuyapi.commons.exceptions.GeneralException;

public class InvalidPatternOfPasswordException extends GeneralException {
    public InvalidPatternOfPasswordException(String errorCode, String message) {
        super(errorCode, message);
    }
}
