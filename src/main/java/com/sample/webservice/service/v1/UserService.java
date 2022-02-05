package com.sample.webservice.service.v1;

import com.sample.webservice.entity.Users;
import com.sample.webservice.models.ApiSuccessResponse;
import com.sample.webservice.models.v1.UserCreateModel;
import com.sample.webservice.models.v1.UserUpdateModel;

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
    UserCreateModel createUserAPIOutData(Users user);

    /**
     * Service to create new user
     *
     * @param user
     * @param apiRequestedUserId
     * @return
     */
    ApiSuccessResponse createUser(UserCreateModel user, long apiRequestedUserId);

    /**
     * To list all users
     *
     * @param apiRequestedUserId
     * @return
     */
    ApiSuccessResponse listAllUsers(long apiRequestedUserId);

    /**
     * To get a single user using id
     *
     * @param userId
     * @param apiRequestedUserId
     * @return
     */
    ApiSuccessResponse getUserById(long userId, long apiRequestedUserId);


    /**
     * To find the user by username
     *
     * @param username
     * @return
     */
    Users findByUsername(String username);

    /**
     * To update existing user
     * @param userUpdate
     * @param apiRequestedUserId
     * @return
     */
    ApiSuccessResponse updateUser(UserUpdateModel userUpdate, long apiRequestedUserId);

    /**
     * To delete a user
     * @param id
     * @return
     */
    ApiSuccessResponse deleteUser(long id,long apiRequestedUserId);
}
