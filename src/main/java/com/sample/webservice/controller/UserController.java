package com.sample.webservice.controller;

import com.sample.webservice.models.ApiResponse;
import com.sample.webservice.models.CustomUser;
import com.sample.webservice.service.GeneralServices;
import com.sample.webservice.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller class for receiving user related APIs.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
@RequestMapping("/api/v1/user")
@RestController
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private GeneralServices generalServices;

    @Autowired
    private UserService userService;

    /**
     * Web service to create new user
     *
     * @param httpServletRequest
     * @param user
     * @return
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createUser(HttpServletRequest httpServletRequest,
                                             @RequestBody CustomUser user) {
        logger.info("<--- Service to save new user request : received --->");
        ApiResponse apiResponse = userService.createUser(user, generalServices.getApiRequestedUserId(httpServletRequest));
        logger.info("<--- Service to save new user response : given --->");
        return ResponseEntity.status(HttpStatus.CREATED).body(generalServices.buildJsonData(apiResponse));

    }


    /**
     * Web service to list all users
     *
     * @param httpServletRequest
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> listUsers(HttpServletRequest httpServletRequest) {
        logger.info("<--- Service to list users request : received --->");
        ApiResponse apiResponse = userService.listAllUsers(generalServices.getApiRequestedUserId(httpServletRequest));
        logger.info("<--- Service to list users response : given --->");
        return ResponseEntity.status(HttpStatus.OK).body(generalServices.buildJsonData(apiResponse));
    }


    /**
     * Web service to get a single user by id
     *
     * @param httpServletRequest
     * @param id
     * @return
     */
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getUserById(HttpServletRequest httpServletRequest,
                                              @PathVariable long id) {
        logger.info("<--- Service to get single user request : received --->");
        ApiResponse apiResponse = userService.getUserById(id, generalServices.getApiRequestedUserId(httpServletRequest));
        logger.info("<--- Service to get single user response : given --->");
        return ResponseEntity.status(HttpStatus.OK).body(generalServices.buildJsonData(apiResponse));
    }

}
