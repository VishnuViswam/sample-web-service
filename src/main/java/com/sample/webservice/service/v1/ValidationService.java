package com.sample.webservice.service.v1;

import com.sample.webservice.models.v1.UserCreateModel;
import com.sample.webservice.models.Tokens;

/**
 * Interface to declare the validation services .
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
public interface ValidationService {
    /**
     * To validate user login credentials
     *
     * @param username
     * @param password
     * @return
     */
    boolean validateUserLogin(String username, String password);

    /**
     * To validate JWT tokens
     *
     * @param tokens
     * @return
     */
    boolean validateTokens(Tokens tokens);


    /**
     * To validate user creation API
     *
     * @param user
     * @return
     */
    boolean validateUserCreation(UserCreateModel user);

}
