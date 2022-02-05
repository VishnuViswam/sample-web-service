package com.sample.webservice.serviceimpl.v1;

import com.sample.webservice.entity.AppConfigSettings;
import com.sample.webservice.entity.UserAccounts;
import com.sample.webservice.entity.Users;
import com.sample.webservice.exceptions.UnknownException;
import com.sample.webservice.models.ApiResponseWithCode;
import com.sample.webservice.models.ApiSuccessResponse;
import com.sample.webservice.models.Tokens;
import com.sample.webservice.security.JwtTokenHandler;
import com.sample.webservice.security.PasswordEncryptingService;
import com.sample.webservice.service.v1.*;
import com.sample.webservice.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(UnAuthorisedAccessServiceImpl.class);

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
    public ApiSuccessResponse userLoginService(String username, String password) {
        Tokens tokens = null;
        Users user = userService.findByUsername(username);
        if (user != null) {
            if (passwordEncryptingService.matches(password,
                    user.getUserAccount().getPassword())) {
                if (user.getUserAccount().getStatus() == Constants.ACTIVE_STATUS) {
                    String roleName = user.getUserAccount().getUserRole().getRoleName();
                    // Creating new tokens
                    try {
                        tokens = createTokens(user.getUserAccount().getId().toString(), roleName);
                    } catch (Exception exception) {
                        logger.error("Token creation failed : ", exception);
                        throw new UnknownException();
                    }

                    // Validating tokens
                    if (validationService.validateTokens(tokens)) {
                        tokens.setUserId(user.getUserAccount().getId());
                        return new ApiSuccessResponse(tokens);

                    } else {
                        throw new UnknownException();
                    }

                } else {
                    return new ApiSuccessResponse(new ApiResponseWithCode(Constants.USER_ACCOUNT_IS_INACTIVE_ERROR_CODE,
                            Constants.USER_ACCOUNT_IS_INACTIVE_ERROR_MESSAGE));
                }

            } else {
                return new ApiSuccessResponse(new ApiResponseWithCode(Constants.USERNAME_OR_PASSWORD_IS_INCORRECT_ERROR_CODE,
                        Constants.USERNAME_OR_PASSWORD_IS_INCORRECT_ERROR_MESSAGE));
            }

        } else {
            return new ApiSuccessResponse(new ApiResponseWithCode(Constants.USERNAME_OR_PASSWORD_IS_INCORRECT_ERROR_CODE,
                    Constants.USERNAME_OR_PASSWORD_IS_INCORRECT_ERROR_MESSAGE));
        }
    }

    @Override
    public ApiSuccessResponse createNewAccessTokenUsingRefreshToken(String refreshToken) {
        Tokens tokens = null;
        UserAccounts userAccount = null;
        AppConfigSettings configSettings = appConfigSettingsService.findByConfigKeyAndStatus(Constants.JWT_SECRET_KEY,
                Constants.ACTIVE_STATUS);
        // Validate Refresh token
        userAccount = jwtTokenHandler.validate(configSettings.getConfigValue(), refreshToken);
        if (userAccount != null) {
            // Creating new tokens if provided refresh token is valid
            try {
                tokens = createTokens(userAccount.getId().toString(), userAccount.getRole());
            } catch (Exception exception) {
                logger.error("Token creation failed : ", exception);
                throw new UnknownException();
            }
            if (validationService.validateTokens(tokens)) {
                tokens.setUserId(userAccount.getId());
                return new ApiSuccessResponse(tokens);

            } else {
                throw new UnknownException();
            }
        } else {
            return new ApiSuccessResponse(new ApiResponseWithCode(Constants.REFRESH_TOKEN_EXPIRED_ERROR_CODE,
                    Constants.REFRESH_TOKEN_EXPIRED_ERROR_MESSAGE));
        }
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
