package com.mvnnixbuyapi.commons.utils;

import com.mvnnixbuyapi.commons.dtos.response.GenericResponseForBody;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

public class ResponseUtils {
    public static <T> ResponseEntity<GenericResponseForBody<T>> buildBadRequestResponse(BindingResult bindingResult) {
        List<String> errors = bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(new GenericResponseForBody<>(errors));
    }
    public static <T> ResponseEntity<GenericResponseForBody<T>> buildBadRequestResponse(List<String> errors) {
        return ResponseEntity.badRequest().body(new GenericResponseForBody<>(errors));
    }

    public static <T> ResponseEntity<GenericResponseForBody<T>> buildBadRequestResponse(String error) {
        return ResponseEntity.badRequest().body(new GenericResponseForBody<>(List.of(error)));
    }

    public static <T> ResponseEntity<GenericResponseForBody<T>> buildSuccessResponse(T value) {
        return ResponseEntity.ok().body(new GenericResponseForBody<>(List.of("SUCCESSFUL"), value));
    }
}
