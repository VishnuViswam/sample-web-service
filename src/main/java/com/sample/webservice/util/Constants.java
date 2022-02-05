package com.sample.webservice.util;

/**
 * Class which have all constant values of the application.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
public class Constants {

    // API common error codes and messages
    public static final short UNKNOWN_ERROR_CODE = 0000;
    public static final String UNKNOWN_ERROR_MESSAGE = "Something went wrong";

    public static final short UNAUTHORIZED_ERROR_CODE = 1000;
    public static final String UNAUTHORIZED_ERROR_ERROR_MESSAGE = "Unauthorized request";

    public static final short INVALID_TOKEN_ERROR_CODE = 1001;
    public static final String INVALID_TOKEN_ERROR_ERROR_MESSAGE = "Invalid token";

    public static final short WRONG_HTTP_METHOD = 1002;
    public static final String WRONG_HTTP_METHOD_ERROR_MESSAGE = "HTTP method is wrong";

    public static final short MANDATORY_FIELDS_ARE_NOT_PRESENT_CODE = 1003;
    public static final String MANDATORY_FIELDS_ARE_NOT_PRESENT_ERROR_MESSAGE = "Mandatory fields are not present";

    public static final short INCORRECT_DATA_CODE = 1004;
    public static final String INCORRECT_DATA_MESSAGE = "Incorrect data";

    public static final short NO_DATA_FOUND_CODE = 1005;
    public static final String NO_DATA_FOUND_MESSAGE = "No data found";

    public static final short USERNAME_OR_PASSWORD_IS_INCORRECT_ERROR_CODE = 1006;
    public static final String USERNAME_OR_PASSWORD_IS_INCORRECT_ERROR_MESSAGE = "Username or password is incorrect";

    public static final short USER_ACCOUNT_IS_INACTIVE_ERROR_CODE = 1007;
    public static final String USER_ACCOUNT_IS_INACTIVE_ERROR_MESSAGE = "User account is inactive";

    public static final short REFRESH_TOKEN_EXPIRED_ERROR_CODE = 1008;
    public static final String REFRESH_TOKEN_EXPIRED_ERROR_MESSAGE = "refresh token is expired";

    public static final short OPERATION_SUCCESS_CODE = 1009;
    public static final String OPERATION_SUCCESS_MESSAGE = "Operation success";

    public static final short OPERATION_FAILED_ERROR_CODE = 1010;
    public static final String OPERATION_FAILED_ERROR_MESSAGE = "Operation failed";

    //Application user roles
    public static final String ROLE_ADMIN = "ADMIN";

    public static final String TOKEN_IS_EXPIRED = "Token is expired";


    // API response messages
//    public static final String SUCCESS_MESSAGE = "Success";
//    public static final String NO_DATA_FOUND_MESSAGE = "No data found";
//    public static final String SERVER_ERROR_MESSAGE = "Something went wrong";
//    public static final String INCORRECT_DATA_MESSAGE = "Incorrect data";
//

//
//    public static final String WRONG_USERNAME_OR_PASSWORD = "Username or password is incorrect";
//
//    public static final String ACCOUNT_IS_DELETED_MESSAGE = "This user account is deleted by admin";

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

    // Servlet Header custom keys
    public static final String API_REQUESTED_USER_ACCOUNT_ID_KEY = "userAccountId";

    //Character length of user properties
    public static final short USER_USERNAME_LENGTH = 5;
    public static final short USER_PASSWORD_LENGTH = 5;
}
