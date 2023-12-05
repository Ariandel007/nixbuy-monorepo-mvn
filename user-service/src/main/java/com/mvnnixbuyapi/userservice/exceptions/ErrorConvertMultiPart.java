package com.mvnnixbuyapi.userservice.exceptions;

import com.mvnnixbuyapi.commons.exceptions.GeneralException;

public class ErrorConvertMultiPart extends GeneralException {
    public ErrorConvertMultiPart(String errorCode, String message) {
        super(errorCode, message);
    }
}
