package com.sample.webservice.models;

import java.io.Serializable;

/**
 * When API is failed ,
 * This class will be returned to the client as API response.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2022-02-05
 */
public class ApiErrorResponse implements Serializable {

    private short errorCode;

    private String errorMessage;

    private long timestamp;

    public ApiErrorResponse(short errorCode, String errorMessage, long timestamp) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.timestamp = timestamp;
    }

    public short getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(short errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
