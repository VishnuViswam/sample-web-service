package com.sample.webservice.security;

import com.sample.webservice.models.JwtUserDetails;
import com.sample.webservice.util.Constants;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Successfully authenticated web service requests will reach this class.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
public class JwtSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
        httpServletRequest.setAttribute(Constants.API_REQUESTED_USER_ACCOUNT_ID_KEY, jwtUserDetails.getUserId());
    }
}