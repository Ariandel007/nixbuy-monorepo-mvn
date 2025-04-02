package com.mvnnixbuyapi.commons.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;

public class JsonUtils {
    public static String convertBase64JsonToJson(String base64Json) {
        return new String(Base64.getDecoder().decode(base64Json));
    }

    public static <T> T fromJson(String json,
                                 Class<T> classToConvert,
                                 ObjectMapper mapper) throws JsonProcessingException {
        return mapper.readValue(json, classToConvert);
    }

    public static <T> byte[] toJsonBytes(T object, ObjectMapper mapper) throws JsonProcessingException {
        return mapper.writeValueAsBytes(object);
    }
}
