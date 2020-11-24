package com.sample.webservice.serviceimpl;

import com.sample.webservice.entity.Roles;
import com.sample.webservice.entity.UserAccounts;
import com.sample.webservice.entity.Users;
import com.sample.webservice.models.ApiResponse;
import com.sample.webservice.models.CustomUser;
import com.sample.webservice.repositories.RolesRepository;
import com.sample.webservice.repositories.UserRepository;
import com.sample.webservice.security.PasswordEncryptingService;
import com.sample.webservice.service.GeneralServices;
import com.sample.webservice.service.UserAccountsService;
import com.sample.webservice.service.UserService;
import com.sample.webservice.service.ValidationService;
import com.sample.webservice.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Service class to implement the user operations.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
@Service("UserService")
@Scope("prototype")
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private GeneralServices generalServices;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAccountsService userAccountsService;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncryptingService passwordEncryptingService;

    @Override
    public ApiResponse createUser(CustomUser customUser, long apiRequestedUserId) {
        ApiResponse apiResponse = new ApiResponse(Constants.SERVER_ERROR_MESSAGE, false);
        UserAccounts newUserUserAccounts = null;
        Users user = null;
        UserAccounts userAccount = null;
        Roles role = null;
        try {
            // Validating the received object in API
            if (validationService.validateUserCreation(customUser)) {
                // checking the API requested User details
                userAccount = userAccountsService.findByIdAndStatus(apiRequestedUserId, Constants.ACTIVE_STATUS);
                if (userAccount != null && userAccount.getUserRole().getRoleName().equals(Constants.ROLE_ADMIN)) {

                    role = rolesRepository.findByIdAndStatus(customUser.getRoleId(), Constants.ACTIVE_STATUS);
                    newUserUserAccounts = new UserAccounts(customUser.getUsername(), Constants.ACTIVE_STATUS,
                            role, Calendar.getInstance(), apiRequestedUserId);

                    // Encoding password
                    newUserUserAccounts.setPassword(passwordEncryptingService.encode(customUser.getPassword()));
                    userAccountsService.save(newUserUserAccounts);

                    user = new Users(newUserUserAccounts, customUser.getFullName(), customUser.getEmailAddress(), customUser.getPhoneNumber(),
                            Constants.ACTIVE_STATUS, Calendar.getInstance(), apiRequestedUserId);
                    userRepository.save(user);

                    customUser.setPassword(null);
                    customUser.setUserId(newUserUserAccounts.getId());
                    customUser.setStatus(newUserUserAccounts.getStatus());
                    apiResponse.setMessage(Constants.SUCCESS_MESSAGE);
                    apiResponse.setStatus(true);
                    apiResponse.setData(customUser);
                } else {
                    apiResponse.setMessage(Constants.INCORRECT_DATA_MESSAGE);
                    apiResponse.setStatus(false);
                }

            } else {
                apiResponse.setMessage(Constants.INCORRECT_DATA_MESSAGE);
                apiResponse.setStatus(false);
            }

        } catch (Exception e) {
            apiResponse.setMessage(Constants.SERVER_ERROR_MESSAGE);
            apiResponse.setStatus(false);
            logger.error("Save new user : exception : ", e);
        }

        return apiResponse;
    }

    @Override
    public ApiResponse listAllUsers(long apiRequestedUserId) {
        ApiResponse apiResponse = new ApiResponse();
        List<CustomUser> userListOut = null;
        List<Users> userList = null;
        try {
            UserAccounts userAccounts = userAccountsService.findByIdAndStatus(apiRequestedUserId, Constants.ACTIVE_STATUS);
            if (userAccounts.getUserRole().getRoleName().equals(Constants.ROLE_ADMIN)) {
                userList = userRepository.findAll();
                if (userList != null && userList.size() != 0) {
                    userListOut = new ArrayList<>();
                    for (Users users : userList) {
                        CustomUser customUser = createUserAPIOutData(users);
                        userListOut.add(customUser);
                    }
                    apiResponse.setData(userListOut);
                    apiResponse.setMessage(Constants.SUCCESS_MESSAGE);
                    apiResponse.setStatus(true);
                } else {
                    apiResponse.setMessage(Constants.NO_DATA_FOUND_MESSAGE);
                    apiResponse.setStatus(false);
                }
            } else {
                apiResponse.setMessage(Constants.INCORRECT_DATA_MESSAGE);
                apiResponse.setStatus(false);
            }

        } catch (Exception e) {
            apiResponse.setStatus(false);
            apiResponse.setMessage(Constants.SERVER_ERROR_MESSAGE);
            logger.error("List users : exception : ", e);
        }

        return apiResponse;
    }

    @Override
    public ApiResponse getUserById(long userId, long apiRequestedUserId) {
        ApiResponse apiResponse = new ApiResponse();
        Users user = null;
        CustomUser customUser = null;
        try {
            UserAccounts userAccounts = userAccountsService.findByIdAndStatus(apiRequestedUserId, Constants.ACTIVE_STATUS);
            if (userAccounts.getUserRole().getRoleName().equals(Constants.ROLE_ADMIN) || userAccounts.getId() == userId) {
                List<Short> statusList = new ArrayList<>();
                statusList.add(Constants.ACTIVE_STATUS);
                user = userRepository.findByUserAccountIdAndUserAccountStatusIn(userId, statusList);
                if (user != null) {
                    customUser = createUserAPIOutData(user);
                    apiResponse.setStatus(true);
                    apiResponse.setMessage(Constants.SUCCESS_MESSAGE);
                    apiResponse.setData(customUser);
                } else {
                    apiResponse.setStatus(false);
                    apiResponse.setMessage(Constants.NO_DATA_FOUND_MESSAGE);
                }
            } else {
                apiResponse.setStatus(false);
                apiResponse.setMessage(Constants.INCORRECT_DATA_MESSAGE);
            }
        } catch (Exception e) {
            apiResponse.setStatus(false);
            apiResponse.setMessage(Constants.SERVER_ERROR_MESSAGE);
            logger.error("Get user by id : exception : ", e);
        }
        return apiResponse;
    }


    @Override
    public Users findByUsername(String username) {
        return userRepository.findByUserAccountUsername(username);
    }


    @Override
    public CustomUser createUserAPIOutData(Users user) {
        CustomUser customUser = new CustomUser();
        customUser.setUserId(user.getId());
        customUser.setFullName(user.getFullName());
        customUser.setEmailAddress(user.getEmailAddress());
        customUser.setPhoneNumber(user.getPhoneNumber());
        customUser.setRoleId(user.getUserAccount().getUserRole().getId());
        customUser.setCreatedDate(user.getCreatedDate());
        customUser.setStatus(user.getStatus());
        return customUser;
    }
}

