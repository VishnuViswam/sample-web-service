package com.sample.webservice.service;

import com.sample.webservice.models.ApiResponse;
import com.sample.webservice.models.CustomUser;

/**
 * Interface to declare the unauthorized API services .
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
public interface UnAuthorisedAccessService {

    /**
     * Service to login a user.
     *
     * @param username
     * @param password
     * @return
     */
    ApiResponse userLoginService(String username, String password);

    /**
     * Service to create new access token using refresh token
     *
     * @param refreshToken
     * @return
     */
    ApiResponse createNewAccessTokenUsingRefreshToken(String refreshToken);
}
