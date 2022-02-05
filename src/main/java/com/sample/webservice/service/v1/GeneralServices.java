package com.sample.webservice.service.v1;


import javax.servlet.http.HttpServletRequest;

/**
 * Interface to declare the common operations .
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
public interface GeneralServices {

    /**
     * To convert any object to json
     *
     * @param object
     * @return
     */
    String buildJsonData(Object object);

    /**
     * To collect user account id from api request
     *
     * @param httpServletRequest
     * @return
     */
    long getApiRequestedUserId(HttpServletRequest httpServletRequest);
}
