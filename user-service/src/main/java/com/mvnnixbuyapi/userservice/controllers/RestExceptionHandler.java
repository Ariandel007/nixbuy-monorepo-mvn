package com.mvnnixbuyapi.userservice.controllers;

import com.mvnnixbuyapi.commons.exceptions.GeneralBodyExceptionHandler;
import com.mvnnixbuyapi.commons.exceptions.GeneralException;
import com.mvnnixbuyapi.userservice.exceptions.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(
            value= {
                    InvalidUserToRegisterException.class,
                    UserAlreadyExistsException.class,
                    EmptyPasswordException.class,
                    InvalidPatternOfPasswordException.class,
                    InvalidRangeOfPasswordException.class
            })
    protected ResponseEntity<Object> handleBadRequest(GeneralException ex, WebRequest request) {
        return handleExceptionInternal(
                ex,
                new GeneralBodyExceptionHandler(ex.getErrorCode(),ex.getMessage(),ex.getLocalizedMessage()),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value= {UserToUpdateNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(GeneralException ex, WebRequest request) {
        return handleExceptionInternal(
                ex,
                new GeneralBodyExceptionHandler(ex.getErrorCode(),ex.getMessage(),ex.getLocalizedMessage()),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND, request);
    }
}
