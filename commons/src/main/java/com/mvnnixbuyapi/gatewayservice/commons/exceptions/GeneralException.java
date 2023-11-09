package com.mvnnixbuyapi.gatewayservice.commons.exceptions;

public class GeneralException extends RuntimeException {
    private String errorCode;
    public GeneralException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
