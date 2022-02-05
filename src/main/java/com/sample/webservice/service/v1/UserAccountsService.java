package com.sample.webservice.service.v1;

import com.sample.webservice.entity.UserAccounts;

/**
 * Interface to declare the user account operations.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
public interface UserAccountsService {
    /**
     * To finnd object using id and status
     *
     * @param userId
     * @param status
     * @return
     */
    UserAccounts findByIdAndStatus(long userId, short status);

    /**
     * To save User account object.
     *
     * @param newUserUserAccounts
     */
    void save(UserAccounts newUserUserAccounts);
}
