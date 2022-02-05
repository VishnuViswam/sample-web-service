package com.sample.webservice.service.v1;

import com.sample.webservice.models.ApiSuccessResponse;

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
    ApiSuccessResponse userLoginService(String username, String password);

    /**
     * Service to create new access token using refresh token
     *
     * @param refreshToken
     * @return
     */
    ApiSuccessResponse createNewAccessTokenUsingRefreshToken(String refreshToken);
}
