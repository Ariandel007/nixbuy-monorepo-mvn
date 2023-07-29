package com.mvnnixbuyapi.commons.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {
    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Username shouldn't exceed 50 characters")
    private String username;
    @NotBlank(message = "Password is required")
    @Size(min = 12, max = 128, message = "Username should be between 12 and 128 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\p{Punct}])[A-Za-z\\d\\p{Punct}]+$",
              message = "Should contain atleast 1 uppercase, 1 lowercase, 1 digit and 1 special char")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email isn't valid")
    private String email;

    @NotBlank(message = "Firstname is required")
    @Size(min = 1, max = 255, message = "Firstname should be between 1 and 255 characters")
    private String firstname;
    @NotBlank(message = "Lastname is required")
    @Size(min = 1, max = 255, message = "Lastname should be between 1 and 255 characters")
    private String lastname;

    @NotBlank(message = "Country is required")
    @Size(min = 1, max = 255, message = "Country should be between 1 and 255 characters")
    private String country;

    @NotBlank(message = "City is required")
    @Size(min = 1, max = 255, message = "City should be between 1 and 255 characters")
    private String city;
    @NotBlank(message = "City is required")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$",
            message = "Format should be UTC (yyyy-MM-dd'T'HH:mm:ss'Z')")
    private String birthDateUtc;

}
