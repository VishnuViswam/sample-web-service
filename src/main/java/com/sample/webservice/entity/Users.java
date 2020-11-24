package com.sample.webservice.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Entity class used to handle user basic details.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
@Entity
@Table(name = "tbl_users")
public class Users implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_account_id", columnDefinition = "bigint", nullable = false)
    private UserAccounts userAccount;

    @Column(name = "full_name", nullable = false, columnDefinition = "varchar(250)")
    private String fullName;

    @Column(name = "email_address", nullable = true, columnDefinition = "varchar(250)")
    private String emailAddress;

    @Column(name = "phone_number", nullable = true, columnDefinition = "bigint")
    private Long phoneNumber;

    @Column(name = "status", nullable = false, columnDefinition = "smallint")
    private Short status;

    @Column(name = "created_date", nullable = false, columnDefinition = "datetime")
    private Calendar createdDate;

    @Column(name = "created_by", nullable = false, columnDefinition = "bigint")
    private Long createdBy;

    @Column(name = "updated_date", nullable = true, columnDefinition = "datetime")
    private Calendar updatedDate;

    @Column(name = "updated_by", nullable = true, columnDefinition = "bigint")
    private Long updatedBy;


    @Transient
    private int roleId;

    @Transient
    private String date;


    public Users() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Users(UserAccounts userAccount, String fullName, String emailAddress, Long phoneNumber,
                 Short status, Calendar createdDate, Long createdBy) {
        super();
        this.userAccount = userAccount;
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public UserAccounts getUserAccount() {
        return userAccount;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setUserAccount(UserAccounts userAccount) {
        this.userAccount = userAccount;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    public Calendar getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Calendar updatedDate) {
        this.updatedDate = updatedDate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

}
