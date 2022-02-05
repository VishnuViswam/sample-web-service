package com.sample.webservice.controller.v1;

import com.sample.webservice.models.ApiSuccessResponse;
import com.sample.webservice.models.v1.UserCreateModel;
import com.sample.webservice.models.v1.UserUpdateModel;
import com.sample.webservice.service.v1.GeneralServices;
import com.sample.webservice.service.v1.UserService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * Controller class for receiving user related APIs.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
@RequestMapping("/api/v1/user")
@RestController
@Validated
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

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
                                             @Valid @RequestBody UserCreateModel user) {
        logger.debug("<--- Service to save new user request : received --->");
        ApiSuccessResponse apiResponse = userService.createUser(user, generalServices.getApiRequestedUserId(httpServletRequest));
        logger.debug("<--- Service to save new user response : given --->");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

    }


    /**
     * Web service to list all users
     *
     * @param httpServletRequest
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> listUsers(HttpServletRequest httpServletRequest) {
        logger.debug("<--- Service to list users request : received --->");
        ApiSuccessResponse apiResponse = userService.listAllUsers(generalServices.getApiRequestedUserId(httpServletRequest));
        logger.debug("<--- Service to list users response : given --->");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
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
                                              @PathVariable("id") @Min(1) long id) {
        logger.debug("<--- Service to get single user request : received --->");
        ApiSuccessResponse apiResponse = userService.getUserById(id, generalServices.getApiRequestedUserId(httpServletRequest));
        logger.debug("<--- Service to get single user response : given --->");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /**
     * Web service to update existing user
     *
     * @param httpServletRequest
     * @param userUpdate
     * @return
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateUser(HttpServletRequest httpServletRequest,
                                             @Valid @RequestBody UserUpdateModel userUpdate) {
        logger.debug("<--- Service to update existing user request : received --->");
        ApiSuccessResponse apiResponse = userService.updateUser(userUpdate, generalServices.getApiRequestedUserId(httpServletRequest));
        logger.debug("<--- Service to update existing user response : given --->");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }

    /**
     * Web service to delete existing user
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteUser(HttpServletRequest httpServletRequest,
                                             @PathVariable("id") @Min(2) long id) {
        logger.debug("<--- Service to update existing user request : received --->");
        ApiSuccessResponse apiResponse = userService.deleteUser(id, generalServices.getApiRequestedUserId(httpServletRequest));
        logger.debug("<--- Service to update existing user response : given --->");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }

}
