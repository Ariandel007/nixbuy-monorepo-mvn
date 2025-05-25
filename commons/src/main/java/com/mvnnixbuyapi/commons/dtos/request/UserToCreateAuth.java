package com.mvnnixbuyapi.commons.dtos.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserToCreateAuth {
    private String username;
    private String email;
    private String authType;

}
