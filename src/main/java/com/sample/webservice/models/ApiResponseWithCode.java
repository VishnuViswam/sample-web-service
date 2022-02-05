package com.sample.webservice.models;

public class ApiResponseWithCode {

    private short code;
    private String message;

    public short getCode() {
        return code;
    }

    public ApiResponseWithCode(short code, String message) {
        this.code = code;
        this.message = message;
    }

    public void setCode(short code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
