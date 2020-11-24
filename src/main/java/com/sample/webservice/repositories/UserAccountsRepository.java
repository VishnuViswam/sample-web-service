package com.sample.webservice.repositories;

import com.sample.webservice.entity.UserAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface used to handle User Account details from DB.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
@Repository("UserAccountsRepository")
public interface UserAccountsRepository extends JpaRepository<UserAccounts, Long> {

    /**
     * To find object by id and status
     *
     * @param userId
     * @param activeStatus
     * @return
     */
    UserAccounts findByIdAndStatus(Long userId, short activeStatus);

}
