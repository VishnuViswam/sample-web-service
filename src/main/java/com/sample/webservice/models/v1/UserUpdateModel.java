package com.sample.webservice.models.v1;


import javax.validation.constraints.Min;

/**
 * Model class used to handle User update information.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2022-02-06
 */
public class UserUpdateModel {

    @Min(1)
    private long userId;

    private String username;

    private String fullName;

    private String emailAddress;

    private Long phoneNumber;

    public UserUpdateModel() {
    }

    public String getUsername() {
        return username;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
