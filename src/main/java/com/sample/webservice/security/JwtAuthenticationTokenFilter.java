package com.sample.webservice.security;

import com.sample.webservice.exceptions.UnknownException;
import com.sample.webservice.models.ApiResponseWithCode;
import com.sample.webservice.models.JwtAuthenticationToken;
import com.sample.webservice.service.v1.GeneralServices;
import com.sample.webservice.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class used to handle JWT configuration by filtering every webservice received in the server.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GeneralServices generalServices;

    public JwtAuthenticationTokenFilter() {
        // This configuration will filter every webservice request which have the following name in the URL.
        super("/api/**");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {

        String authorization = httpServletRequest.getHeader(Constants.AUTHORIZATION__HEADER_KEY);
        if (authorization == null || authorization.isBlank() ||
                !authorization.startsWith(Constants.AUTHORIZATION__HEADER_VALUE_STARTING_BEARER)) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpServletResponse.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            httpServletResponse.getWriter().write(generalServices.buildJsonData(
                    new ApiResponseWithCode(Constants.UNAUTHORIZED_ERROR_CODE, Constants.UNAUTHORIZED_ERROR_ERROR_MESSAGE)));
            httpServletResponse.getWriter().flush();
            httpServletResponse.getWriter().close();
            logger.error("Authentication : Error : Invalid in the requests");
            return null;
        } else {
            String authenticationToken = authorization.substring(6);
            JwtAuthenticationToken token = new JwtAuthenticationToken(authenticationToken);
            Authentication authentication = null;
            try {
                authentication = getAuthenticationManager().authenticate(token);
            } catch (Exception e) {
                if (e.getMessage().equals(Constants.TOKEN_IS_EXPIRED)) {
                    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    httpServletResponse.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                    httpServletResponse.getWriter().write(generalServices.buildJsonData(
                            new ApiResponseWithCode(Constants.INVALID_TOKEN_ERROR_CODE, Constants.INVALID_TOKEN_ERROR_ERROR_MESSAGE)
                    ));
                    httpServletResponse.getWriter().flush();
                    httpServletResponse.getWriter().close();
                } else {
                    throw new UnknownException();
                }
            }

            return authentication;

        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }
}
