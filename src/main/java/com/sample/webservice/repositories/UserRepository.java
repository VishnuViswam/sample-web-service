package com.sample.webservice.repositories;

import com.sample.webservice.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface used to handle User basic details from DB.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
public interface UserRepository extends JpaRepository<Users, Long> {

    /**
     * To find user object using username
     *
     * @param username
     * @return
     */
    Users findByUserAccountUsername(String username);


    /**
     * To find user object using user account id and active status list
     *
     * @param userId
     * @param statusList
     * @return
     */
    Users findByUserAccountIdAndUserAccountStatusIn(long userId, List<Short> statusList);
}
