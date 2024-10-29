package com.mvnnixbuyapi.commons.utils;

import java.util.Base64;

public class JsonUtils {
    public static String convertBase64JsonToJson(String base64Json) {
        return new String(Base64.getDecoder().decode(base64Json));
    }
}
