package com.sample.webservice.controller;


import com.sample.webservice.models.ApiResponse;
import com.sample.webservice.service.GeneralServices;
import com.sample.webservice.service.UnAuthorisedAccessService;
import com.sample.webservice.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for receiving unauthorized APIs.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
@RestController
public class UnAuthorizedAccessController {

    private static final Logger logger = LogManager.getLogger(UnAuthorizedAccessController.class);

    @Autowired
    private UnAuthorisedAccessService unAuthorisedAccessService;

    @Autowired
    private GeneralServices generalService;

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
    public ResponseEntity<Object> userLogin(@RequestParam(name = "username", required = true) String username,
                                            @RequestParam(name = "password", required = true) String password) {
        logger.info("<--- Login request : received --->");
        ApiResponse apiResponse = unAuthorisedAccessService.userLoginService(username, password);
        logger.info("<--- Login response : given --->");
        return ResponseEntity.status(HttpStatus.OK).body(generalService.buildJsonData(apiResponse));
    }

    /**
     * Web service to recreate new access token using refresh token
     *
     * @param refreshToken
     * @return
     */
    @PostMapping(value = "/newAccessToken", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> newAccessToken(@RequestParam(name = "refreshToken", required = true) String refreshToken) {
        logger.info("<--- Create new accessToken request : received --->");
        ApiResponse apiResponse = unAuthorisedAccessService.createNewAccessTokenUsingRefreshToken(refreshToken);
        logger.info("<--- Create new accessToken response : given --->");
        return ResponseEntity.status(HttpStatus.OK).body(generalService.buildJsonData(apiResponse));
    }

}
