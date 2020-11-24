package com.sample.webservice.security;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

/**
 * Class used to perform the operations related to the password encryption.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
@Component
public class PasswordEncryptingService {

    /**
     * To encrypt password
     *
     * @param rawPassword
     * @return
     */
    public String encode(CharSequence rawPassword) {
        return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(6));
    }

    /**
     * To cross check the encrypted password
     *
     * @param rawPassword
     * @param encodedPassword
     * @return
     */
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
    }

}
