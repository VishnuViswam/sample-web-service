package com.sample.webservice.serviceimpl.v1;

import com.sample.webservice.entity.UserAccounts;
import com.sample.webservice.repositories.UserAccountsRepository;
import com.sample.webservice.service.v1.UserAccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Service class to implement the user account operations.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
@Service("UserAccountsService")
@Scope("prototype")
public class UserAccountsServiceImpl implements UserAccountsService {

    @Autowired
    private UserAccountsRepository userAccountsRepository;

    @Override
    public UserAccounts findByIdAndStatus(long userId, short status) {
        return userAccountsRepository.findByIdAndStatus(userId, status);
    }

    @Override
    public void save(UserAccounts newUserUserAccounts) {
        userAccountsRepository.save(newUserUserAccounts);
    }
}
