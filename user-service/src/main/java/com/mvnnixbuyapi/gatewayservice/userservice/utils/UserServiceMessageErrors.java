package com.mvnnixbuyapi.gatewayservice.userservice.utils;

public class UserServiceMessageErrors {
    // Username Validations
    public static String INVALID_USERNAME_CODE = "INVALID_USERNAME";

   // Password validations
    public static String INVALID_PASSWORD_CODE = "INVALID_PASSWORD";

    // Email validations
    public static String INVALID_EMAIL_CODE = "INVALID_EMAIL";

    // Firstname validations
    public static String INVALID_FIRSTNAME_CODE = "INVALID_FIRSTNAME";

    // Lastname validations
    public static String INVALID_LASTNAME_CODE = "INVALID_LASTNAME";

    // Country Validation
    public static String INVALID_COUNTRY_CODE = "INVALID_COUNTRY";

    public static String INVALID_CITY_CODE = "INVALID_CITY";


    public static String INVALID_BIRTHDATE_CODE = "INVALID_BIRTHDATE";

    public static String USER_TO_UPDATE_NOT_FOUND = "USER_TO_UPDATE_NOT_FOUND";
    public static String USER_TO_UPDATE_NOT_FOUND_MSG = "The user wanted to update was not found";

    public static String USER_ALREADY_EXISTS = "USER_ALREADY_EXISTS";
    public static String USER_ALREADY_EXISTS_MSG = "Username already exists";


    public static String INVALID_PATTERN_OF_PASSWORD = "INVALID_PATTERN_OF_PASSWORD";
    public static String INVALID_PATTERN_OF_PASSWORD_MSG = "Should contain atleast 1 uppercase, 1 lowercase, 1 digit and 1 special char and between 12 and 128 characters";


}
