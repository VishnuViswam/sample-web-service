package com.sample.webservice.serviceimpl.v1;

import com.sample.webservice.models.v1.UserCreateModel;
import com.sample.webservice.models.Tokens;
import com.sample.webservice.service.v1.ValidationService;
import com.sample.webservice.util.Constants;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Service class to implement the validation operations.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
@Service("ValidationService")
@Scope("prototype")
public class ValidationServiceImpl implements ValidationService {

    @Override
    public boolean validateUserLogin(String username, String password) {
        boolean status = true;
        if (username == null || username.isBlank())
            status = false;
        else if (password == null || password.isBlank())
            status = false;
        return status;
    }

    @Override
    public boolean validateTokens(Tokens tokens) {
        boolean status = true;
        if (tokens == null)
            status = false;
        else if (tokens.getAccessToken() == null || tokens.getAccessToken().isBlank())
            status = false;
        else if (tokens.getRefreshToken() == null || tokens.getRefreshToken().isBlank())
            status = false;

        return status;
    }


    @Override
    public boolean validateUserCreation(UserCreateModel user) {
        boolean status = true;
        if (user.getRoleId() == null || user.getRoleId() < 1)
            status = false;
        else if (user.getUsername() == null || user.getUsername().isBlank() || user.getUsername().length() < Constants.USER_USERNAME_LENGTH)
            status = false;
        else if (user.getPassword() == null || user.getPassword().isBlank() || user.getPassword().length() < Constants.USER_PASSWORD_LENGTH)
            status = false;
        else if (user.getEmailAddress() == null || user.getEmailAddress().isBlank())
            status = false;
        else if (user.getPhoneNumber() <= 0)
            status = false;
        else if (user.getFullName() == null || user.getFullName().isBlank())
            status = false;
        return status;
    }

}
