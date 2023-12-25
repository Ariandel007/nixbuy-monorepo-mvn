package com.mvnnixbuyapi.commons.dtos.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenericResponseForBody<T> extends GenericResponse {
    private T data;
    public GenericResponseForBody(String code, String message) {
        super(code, message);
    }

    public GenericResponseForBody(String code, String message, T data) {
        super(code, message);
        this.data = data;
    }

}
