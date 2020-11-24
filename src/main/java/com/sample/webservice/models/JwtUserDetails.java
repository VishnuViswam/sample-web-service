package com.sample.webservice.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Model class used to handle JWT configuration of user object.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
public class JwtUserDetails implements UserDetails {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private long userId;
    private String userName;
    private String token;
    private String role;
    private Collection<? extends GrantedAuthority> authorities;


    public JwtUserDetails(long userId, String role, String token, List<GrantedAuthority> grantedAuthorities) {

        this.userId = userId;
        this.token = token;
        this.authorities = grantedAuthorities;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public String getToken() {
        return token;
    }

}
