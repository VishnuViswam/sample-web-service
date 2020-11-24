package com.sample.webservice.util;

/**
 * Class which have all constant values of the application.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
public class Constants {

    // API response messages
    public static final String SUCCESS_MESSAGE = "Success";
    public static final String NO_DATA_FOUND_MESSAGE = "No data found";
    public static final String SERVER_ERROR_MESSAGE = "Something went wrong";
    public static final String INCORRECT_DATA_MESSAGE = "Incorrect data";

    //Application user roles
    public static final String ROLE_ADMIN = "ADMIN";

    public static final String WRONG_USERNAME_OR_PASSWORD = "Username or password is incorrect";

    public static final String ACCOUNT_IS_DELETED_MESSAGE = "This user account is deleted by admin";

    // Model active status meaning
    public static final short ACTIVE_STATUS = 1; // Entity is in active status
    public static final short DELETED_STATUS = 0; // Entity is in deleted status

    // JWT
    public static final String JWT_SECRET_KEY = "JWT_SECRET_KEY";
    public static final String JWT_USER_KEY_NAME = "user";
    public static final String JWT_ACCESS_TOKEN_EXPIRATION_TIME = "JWT_ACCESS_TOKEN_EXPIRATION_TIME";
    public static final String JWT_REFRESH_TOKEN_EXPIRATION_TIME = "JWT_REFRESH_TOKEN_EXPIRATION_TIME";
    public static final String UNAUTHORIZED = "UNAUTHORIZED";

    public static final String AUTHORIZATION__HEADER_KEY = "Authorization";
    public static final String AUTHORIZATION__HEADER_VALUE_STARTING_BEARER = "Bearer "; // Space after Bearer is mandatory in this string.


    // JWT Error message
    public static final String TOKEN_IS_MISSING_MESSAGE = "Token is missing";
    public static final String TOKEN_IS_EXPIRED = "Token is expired";

    // Servlet Header custom keys
    public static final String API_REQUESTED_USER_ACCOUNT_ID_KEY = "userAccountId";

    //Character length of user properties
    public static final short USER_USERNAME_LENGTH = 5;
    public static final short USER_PASSWORD_LENGTH = 5;
}
