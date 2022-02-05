package com.sample.webservice.serviceimpl.v1;

import com.sample.webservice.entity.Roles;
import com.sample.webservice.entity.UserAccounts;
import com.sample.webservice.entity.Users;
import com.sample.webservice.exceptions.InCorrectDataException;
import com.sample.webservice.exceptions.NoDataFoundException;
import com.sample.webservice.models.ApiResponseWithCode;
import com.sample.webservice.models.ApiSuccessResponse;
import com.sample.webservice.models.v1.UserCreateModel;
import com.sample.webservice.models.v1.UserUpdateModel;
import com.sample.webservice.repositories.RolesRepository;
import com.sample.webservice.repositories.UserRepository;
import com.sample.webservice.security.PasswordEncryptingService;
import com.sample.webservice.service.v1.GeneralServices;
import com.sample.webservice.service.v1.UserAccountsService;
import com.sample.webservice.service.v1.UserService;
import com.sample.webservice.service.v1.ValidationService;
import com.sample.webservice.util.Constants;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
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
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
    public ApiSuccessResponse createUser(UserCreateModel customUser, long apiRequestedUserId) {
        UserAccounts newUserUserAccounts = null;
        Users user = null;
        UserAccounts userAccount = null;
        Roles role = null;
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

            return new ApiSuccessResponse(customUser);
        } else {
            throw new InCorrectDataException();
        }


    }

    @Override
    public ApiSuccessResponse listAllUsers(long apiRequestedUserId) {
        List<UserCreateModel> userListOut = null;
        List<Users> userList = null;
        UserAccounts userAccounts = userAccountsService.findByIdAndStatus(apiRequestedUserId, Constants.ACTIVE_STATUS);
        if (userAccounts.getUserRole().getRoleName().equals(Constants.ROLE_ADMIN)) {
            userList = userRepository.findAll();
            if (userList != null && userList.size() != 0) {
                userListOut = new ArrayList<>();
                for (Users users : userList) {
                    UserCreateModel customUser = createUserAPIOutData(users);
                    userListOut.add(customUser);
                }
                return new ApiSuccessResponse(userListOut);
            } else {
                throw new NoDataFoundException();
            }
        } else {
            throw new InCorrectDataException();
        }
    }

    @Override
    public ApiSuccessResponse getUserById(long userId, long apiRequestedUserId) {
        Users user = null;
        UserCreateModel customUser = null;
        UserAccounts userAccounts = userAccountsService.findByIdAndStatus(apiRequestedUserId, Constants.ACTIVE_STATUS);
        if (userAccounts.getUserRole().getRoleName().equals(Constants.ROLE_ADMIN) || userAccounts.getId() == userId) {
            List<Short> statusList = new ArrayList<>();
            statusList.add(Constants.ACTIVE_STATUS);
            user = userRepository.findByUserAccountIdAndUserAccountStatusIn(userId, statusList);
            if (user != null) {
                customUser = createUserAPIOutData(user);
                return new ApiSuccessResponse(customUser);
            } else {
                throw new NoDataFoundException();
            }
        } else {
            throw new InCorrectDataException();
        }
    }


    @Override
    public Users findByUsername(String username) {
        return userRepository.findByUserAccountUsername(username);
    }

    @Override
    public ApiSuccessResponse updateUser(UserUpdateModel userUpdate, long apiRequestedUserId) {
        UserAccounts newUserUserAccounts = null;
        Users users = null;
        UserAccounts userAccount = null;
        // checking the API requested User details
        userAccount = userAccountsService.findByIdAndStatus(apiRequestedUserId, Constants.ACTIVE_STATUS);
        if (userAccount != null && userAccount.getUserRole().getRoleName().equals(Constants.ROLE_ADMIN)) {
            users = userRepository.findByUserAccountIdAndUserAccountStatus(userUpdate.getUserId(), Constants.ACTIVE_STATUS);
            UserAccounts userAccounts = null;
            boolean isUpdated = false;
            if (users != null) {
                if (userUpdate.getUsername() != null && !userUpdate.getUsername().isBlank()) {
                    userAccounts = users.getUserAccount();
                    userAccounts.setUsername(userUpdate.getUsername());
                    userAccounts.setUpdatedBy(apiRequestedUserId);
                    userAccounts.setUpdatedDate(Calendar.getInstance());
                    userAccountsService.save(userAccounts);
                }
                if (!userUpdate.getEmailAddress().isBlank()) {
                    users.setEmailAddress(userUpdate.getEmailAddress());
                    isUpdated = true;
                }
                if (!userUpdate.getFullName().isBlank()) {
                    users.setFullName(userUpdate.getFullName());
                    isUpdated = true;
                }
                if (userUpdate.getPhoneNumber() != 0) {
                    users.setPhoneNumber(userUpdate.getPhoneNumber());
                    isUpdated = true;
                }

                if (isUpdated) {
                    users.setUpdatedBy(apiRequestedUserId);
                    users.setUpdatedDate(Calendar.getInstance());
                    userRepository.save(users);
                }
                return new ApiSuccessResponse(
                        new ApiResponseWithCode(Constants.OPERATION_SUCCESS_CODE, Constants.OPERATION_SUCCESS_MESSAGE));

            } else {
                throw new InCorrectDataException();
            }
        } else {
            throw new InCorrectDataException();
        }
    }

    @Override
    public ApiSuccessResponse deleteUser(long id, long apiRequestedUserId) {
        UserAccounts newUserUserAccounts = null;
        Users users = null;
        UserAccounts userAccount = null;
        // checking the API requested User details
        userAccount = userAccountsService.findByIdAndStatus(apiRequestedUserId, Constants.ACTIVE_STATUS);
        if (userAccount != null && userAccount.getUserRole().getRoleName().equals(Constants.ROLE_ADMIN)) {

            users = userRepository.findByUserAccountIdAndUserAccountStatus(id, Constants.ACTIVE_STATUS);
            UserAccounts userAccounts = null;
            if (users != null) {
                userAccounts = users.getUserAccount();
                userAccounts.setStatus(Constants.DELETED_STATUS);
                userAccounts.setUpdatedBy(apiRequestedUserId);
                userAccounts.setCreatedDate(Calendar.getInstance());
                userAccountsService.save(userAccounts);
                return new ApiSuccessResponse(
                        new ApiResponseWithCode(Constants.OPERATION_SUCCESS_CODE, Constants.OPERATION_SUCCESS_MESSAGE));
            } else {
                throw new InCorrectDataException();
            }
        } else {
            throw new InCorrectDataException();
        }

    }


    @Override
    public UserCreateModel createUserAPIOutData(Users user) {
        UserCreateModel customUser = new UserCreateModel();
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

