package com.sample.webservice.controller.v1;


import com.sample.webservice.models.ApiSuccessResponse;
import com.sample.webservice.service.v1.UnAuthorisedAccessService;
import com.sample.webservice.service.v1.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * Controller class for receiving unauthorized APIs.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
@RestController
@Validated
public class UnAuthorizedAccessController {

    private static final Logger logger = LoggerFactory.getLogger(UnAuthorizedAccessController.class);

    @Autowired
    private UnAuthorisedAccessService unAuthorisedAccessService;

    @Autowired
    private UserService userService;


    /**
     * Web service to login a user into application.
     *
     * @param username
     * @param password
     * @return
     */

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> userLogin(@RequestParam(name = "username", required = true) @NotBlank String username,
                                            @RequestParam(name = "password", required = true) @NotBlank String password) {
        logger.debug("<--- Login request : received --->");
        ApiSuccessResponse apiResponse = unAuthorisedAccessService.userLoginService(username, password);
        logger.debug("<--- Login response : given --->");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /**
     * Web service to recreate new access token using refresh token
     *
     * @param refreshToken
     * @return
     */
    @PostMapping(value = "/newAccessToken", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> newAccessToken(@RequestParam(name = "refreshToken", required = true) @NotBlank String refreshToken) {
        logger.debug("<--- Create new accessToken request : received --->");
        ApiSuccessResponse apiResponse = unAuthorisedAccessService.createNewAccessTokenUsingRefreshToken(refreshToken);
        logger.debug("<--- Create new accessToken response : given --->");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
