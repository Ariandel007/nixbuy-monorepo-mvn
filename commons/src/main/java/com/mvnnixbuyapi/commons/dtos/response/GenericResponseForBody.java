package com.mvnnixbuyapi.commons.dtos.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GenericResponseForBody<T> extends GenericResponse {
    private T data;
    public GenericResponseForBody(List<String> code) {
        super(code);
    }

    public GenericResponseForBody(List<String> code, T data) {
        super(code);
        this.data = data;
    }

}
