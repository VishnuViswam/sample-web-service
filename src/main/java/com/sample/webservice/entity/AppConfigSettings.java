package com.sample.webservice.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
/**
 * Entity class used to handle application configurations .
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
@Entity
@Table(name = "tbl_app_config_settings")
public class AppConfigSettings implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int")
    private Integer id;

    @Column(name = "config_key", nullable = false, columnDefinition ="varchar(50)")
    private String configKey;

    @Column(name = "config_value", nullable = false, columnDefinition ="varchar(250)")
    private String configValue;

    @Column(name = "status", nullable = false, columnDefinition ="smallint")
    private Short status;

    @Column(name = "created_date", nullable = false, columnDefinition ="datetime")
    private Calendar createdDate;

    @Column(name = "created_by", nullable = false, columnDefinition ="bigint")
    private Integer createdBy;

    @Column(name = "updated_date", nullable = true, columnDefinition ="datetime")
    private Calendar updatedDate;

    @Column(name = "updated_by", nullable = true, columnDefinition ="bigint")
    private Integer updatedBy;

    public AppConfigSettings() {
        super();
        // TODO Auto-generated constructor stub
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
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

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Calendar getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Calendar updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }
}

