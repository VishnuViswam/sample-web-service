package com.sample.webservice.service;

import com.sample.webservice.entity.Users;
import com.sample.webservice.models.ApiResponse;
import com.sample.webservice.models.CustomUser;

/**
 * Interface to declare the user operations.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
public interface UserService {

    /**
     * To prepare user object for out put
     *
     * @param user
     * @return
     */
    CustomUser createUserAPIOutData(Users user);

    /**
     * Service to create new user
     *
     * @param user
     * @param apiRequestedUserId
     * @return
     */
    ApiResponse createUser(CustomUser user, long apiRequestedUserId);

    /**
     * To list all users
     *
     * @param apiRequestedUserId
     * @return
     */
    ApiResponse listAllUsers(long apiRequestedUserId);

    /**
     * To get a single user using id
     *
     * @param userId
     * @param apiRequestedUserId
     * @return
     */
    ApiResponse getUserById(long userId, long apiRequestedUserId);


    /**
     * To find the user by username
     *
     * @param username
     * @return
     */
    Users findByUsername(String username);
}
