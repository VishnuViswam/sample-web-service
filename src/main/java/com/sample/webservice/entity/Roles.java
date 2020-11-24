package com.sample.webservice.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Entity class used to handle user roles  .
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
@Entity
@Table(name = "tbl_roles")
public class Roles implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int")
    private Integer id;

    @Column(name = "role_name", nullable = false, columnDefinition = "varchar(50)")
    private String roleName;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

}

