package com.sample.webservice.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
/**
 * Entity class used to handle user accounts important details .
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
@Entity
@Table(name = "tbl_user_accounts")
public class UserAccounts implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint")
    private Long id;

    @Column(name = "username", nullable = false, columnDefinition = "varchar(100)")
    String username;

    @Column(name = "password", nullable = false, columnDefinition = "varchar(250)")
    private String password;

    @Column(name = "status", nullable = false, columnDefinition = "smallint")
    private Short status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false, columnDefinition = "int")
    private Roles userRole;

    @Column(name = "created_date", nullable = false, columnDefinition = "datetime")
    private Calendar createdDate;

    @Column(name = "created_by", nullable = false, columnDefinition = "bigint")
    private Long createdBy;

    @Column(name = "updated_date", nullable = true, columnDefinition = "datetime")
    private Calendar updatedDate;

    @Column(name = "updated_by", nullable = true, columnDefinition = "bigint")
    private Long updatedBy;

    @Transient
    private String role;


    public UserAccounts() {
        super();
        // TODO Auto-generated constructor stub
    }

    public UserAccounts(String username, Short status, Roles userRole, Calendar createdDate, Long createdBy) {
        super();
        this.username = username;
        this.status = status;
        this.userRole = userRole;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
    }

    public Roles getUserRole() {
        return userRole;
    }

    public void setUserRole(Roles userRole) {
        this.userRole = userRole;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

}
