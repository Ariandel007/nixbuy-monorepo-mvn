package com.mvnnixbuyapi.commons.dtos.request;

import lombok.Data;

@Data
public class UserToCreateAuth {
    private String username;
    private String email;
    private boolean deleted = false;
    private boolean blocked = false;
    private String authType;

}
