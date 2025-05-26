package com.mvnnixbuyapi.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
    private final Long id;
    private final Boolean twoFAActivated;
    private final Boolean twoFaRegistered;
    private final Boolean securityQuestionEnabled;
    private final String securityQuestion;
    private final String answer;
    private final String mfaSecret;
    private final String mfaKeyId;
    //TODO: ADD THIS IN THE FEIGN REQUEST AND CONSTRUCTOR

    public CustomUserDetails(Long id,
                             String username,
                             String password,
                             Boolean twoFAActivated,
                             Boolean twoFaRegistered,
                             Boolean securityQuestionEnabled,
                             String securityQuestion,
                             String answer,
                             String mfaSecret,
                             String mfaKeyId,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
        this.twoFAActivated = twoFAActivated;
        this.twoFaRegistered = twoFaRegistered;
        this.securityQuestionEnabled = securityQuestionEnabled;
        this.securityQuestion = securityQuestion;
        this.answer = answer;
        this.mfaSecret = mfaSecret;
        this.mfaKeyId = mfaKeyId;
    }
}
