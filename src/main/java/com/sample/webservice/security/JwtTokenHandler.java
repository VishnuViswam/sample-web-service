package com.sample.webservice.security;

import com.sample.webservice.entity.UserAccounts;
import com.sample.webservice.util.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * For JWT token creation and validation
 *
 * @author AI Center
 */

@Component
public class JwtTokenHandler {


    /**
     * Token validation
     *
     * @param secretKey
     * @param token
     * @return
     */
    public UserAccounts validate(String secretKey, String token) {

        UserAccounts jwtUser = null;
        try {
            Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
            jwtUser = new UserAccounts();
            jwtUser.setId(Long.parseLong(body.getSubject()));
            jwtUser.setRole((String) body.get(Constants.JWT_USER_KEY_NAME));
        } catch (Exception e) {
            System.out.println(e);
        }

        return jwtUser;
    }


    /**
     * To Create JWT token
     *
     * @param secretKey
     * @param userAccountId
     * @param roleName
     * @return
     */
    public String createToken(String secretKey, long accessTokenExpirationTime, String userAccountId, String roleName) {

        Claims claims = Jwts.claims().setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationTime)).setSubject(userAccountId);
        claims.put(Constants.JWT_USER_KEY_NAME, roleName);
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

}
