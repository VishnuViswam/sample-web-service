package com.sample.webservice.models;

import java.io.Serializable;

/**
 * When API is success ,
 * This class will be returned to the client as API response.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2022-02-05
 */
public class ApiSuccessResponse implements Serializable {
    private Object data;

    public ApiSuccessResponse(Object data) {
        this.data = data;
    }

    public ApiSuccessResponse() {
        super();
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
