package com.sample.webservice.serviceimpl;

import com.sample.webservice.entity.AppConfigSettings;
import com.sample.webservice.entity.UserAccounts;
import com.sample.webservice.entity.Users;
import com.sample.webservice.models.CustomUser;
import com.sample.webservice.models.Tokens;
import com.sample.webservice.security.JwtTokenHandler;
import com.sample.webservice.security.PasswordEncryptingService;
import com.sample.webservice.models.ApiResponse;
import com.sample.webservice.service.*;
import com.sample.webservice.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class to implement the unauthorized API services .
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
@Service("UnAuthorisedAccessService")
@Scope("prototype")
public class UnAuthorisedAccessServiceImpl implements UnAuthorisedAccessService {

    private static final Logger logger = LogManager.getLogger(UnAuthorisedAccessServiceImpl.class);

    @Autowired
    private ValidationService validationService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncryptingService passwordEncryptingService;

    @Autowired
    private GeneralServices generalServices;

    @Autowired
    private JwtTokenHandler jwtTokenHandler;

    @Autowired
    private AppConfigSettingsService appConfigSettingsService;

    @Override
    public ApiResponse userLoginService(String username, String password) {
        ApiResponse apiResponse = new ApiResponse();
        Tokens tokens = null;
        CustomUser customUser = null;
        try {
            if (validationService.validateUserLogin(username, password)) {
                Users user = userService.findByUsername(username);
                if (user != null) {
                    if (passwordEncryptingService.matches(password,
                            user.getUserAccount().getPassword())) {
                        if (user.getUserAccount().getStatus() == Constants.ACTIVE_STATUS) {
                            String roleName = user.getUserAccount().getUserRole().getRoleName();
                            // Creating new tokens
                            tokens = createTokens(user.getUserAccount().getId().toString(), roleName);
                            // Validating tokens
                            if (validationService.validateTokens(tokens)) {
                                customUser = userService.createUserAPIOutData(user);
                                apiResponse.setData(customUser);
                                apiResponse.setAccessToken(tokens.getAccessToken());
                                apiResponse.setRefreshToken(tokens.getRefreshToken());
                                apiResponse.setStatus(true);
                                apiResponse.setMessage(Constants.SUCCESS_MESSAGE);

                            } else {
                                apiResponse.setStatus(false);
                                apiResponse.setMessage(Constants.SERVER_ERROR_MESSAGE);
                            }

                        } else if (user.getUserAccount().getStatus() == Constants.DELETED_STATUS) {
                            apiResponse.setStatus(false);
                            apiResponse.setMessage(Constants.ACCOUNT_IS_DELETED_MESSAGE);
                        }

                    } else {
                        apiResponse.setStatus(false);
                        apiResponse.setMessage(Constants.WRONG_USERNAME_OR_PASSWORD);
                    }

                } else {
                    apiResponse.setStatus(false);
                    apiResponse.setMessage(Constants.WRONG_USERNAME_OR_PASSWORD);
                }

            } else {
                apiResponse.setStatus(false);
                apiResponse.setMessage(Constants.INCORRECT_DATA_MESSAGE);
                logger.error("Login : error: Incorrect data from client");
            }
        } catch (Exception e) {
            apiResponse.setStatus(false);
            apiResponse.setMessage(Constants.SERVER_ERROR_MESSAGE);
            logger.error("Login : error: ", e);
        }

        return apiResponse;
    }

    @Override
    public ApiResponse createNewAccessTokenUsingRefreshToken(String refreshToken) {
        ApiResponse apiResponse = new ApiResponse();
        Tokens tokens = null;
        UserAccounts userAccount = null;
        try {

            AppConfigSettings configSettings = appConfigSettingsService.findByConfigKeyAndStatus(Constants.JWT_SECRET_KEY,
                    Constants.ACTIVE_STATUS);
            // Validate Refresh token
            userAccount = jwtTokenHandler.validate(configSettings.getConfigValue(), refreshToken);
            if (userAccount != null) {
                // Creating new tokens if provided refresh token is valid
                tokens = createTokens(userAccount.getId().toString(), userAccount.getRole());
                if (validationService.validateTokens(tokens)) {
                    apiResponse.setAccessToken(tokens.getAccessToken());
                    apiResponse.setRefreshToken(tokens.getRefreshToken());
                    apiResponse.setStatus(true);
                    apiResponse.setMessage(Constants.SUCCESS_MESSAGE);

                } else {
                    apiResponse.setStatus(false);
                    apiResponse.setMessage(Constants.SERVER_ERROR_MESSAGE);
                }
            } else {
                apiResponse.setStatus(false);
                apiResponse.setMessage(Constants.TOKEN_IS_EXPIRED);
            }
        } catch (Exception e) {
            apiResponse.setStatus(false);
            apiResponse.setMessage(Constants.SERVER_ERROR_MESSAGE);
            logger.error("Create new access token : error: ", e);
        }
        return apiResponse;
    }

    /**
     * private Service To create Access token and Refresh token.
     *
     * @param userAccountId
     * @param roleName
     * @return
     * @throws Exception
     */
    private Tokens createTokens(String userAccountId, String roleName) throws Exception {
        Tokens tokens = null;
        List<AppConfigSettings> configSettingsList = new ArrayList<AppConfigSettings>();
        List<String> keyList = new ArrayList<String>();

        keyList.add(Constants.JWT_SECRET_KEY);
        keyList.add(Constants.JWT_ACCESS_TOKEN_EXPIRATION_TIME);
        keyList.add(Constants.JWT_REFRESH_TOKEN_EXPIRATION_TIME);

        configSettingsList = appConfigSettingsService.findByConfigKeyInAndStatus(keyList, Constants.ACTIVE_STATUS);

        if (configSettingsList.size() != 0) {
            tokens = new Tokens();
            String JWT_SECRET_KEY = null;
            String JWT_ACCESS_TOKEN_EXPIRATION_TIME = null;
            String JWT_REFRESH_TOKEN_EXPIRATION_TIME = null;

            for (AppConfigSettings settings : configSettingsList) {

                if (settings.getConfigKey().equals(Constants.JWT_SECRET_KEY)) {
                    JWT_SECRET_KEY = settings.getConfigValue();
                } else if (settings.getConfigKey().equals(Constants.JWT_ACCESS_TOKEN_EXPIRATION_TIME)) {
                    JWT_ACCESS_TOKEN_EXPIRATION_TIME = settings.getConfigValue();
                } else if (settings.getConfigKey().equals(Constants.JWT_REFRESH_TOKEN_EXPIRATION_TIME)) {
                    JWT_REFRESH_TOKEN_EXPIRATION_TIME = settings.getConfigValue();
                }

            }
            tokens.setAccessToken(jwtTokenHandler.createToken(JWT_SECRET_KEY,
                    Long.parseLong(JWT_ACCESS_TOKEN_EXPIRATION_TIME), userAccountId, roleName));
            tokens.setRefreshToken(jwtTokenHandler.createToken(JWT_SECRET_KEY,
                    Long.parseLong(JWT_REFRESH_TOKEN_EXPIRATION_TIME), userAccountId, roleName));

        } else {
            logger.error("Authentication request : token creation failed : no key values found from DB");
        }
        return tokens;

    }

}
