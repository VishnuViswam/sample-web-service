package com.sample.webservice.models;

/**
 * Model class used to handle application web service response.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
public class ApiResponse {

    private String accessToken;

    private String refreshToken;

    private String message;

    private Object data;

    private boolean status;

    public ApiResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    public ApiResponse() {
        super();
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }




}
