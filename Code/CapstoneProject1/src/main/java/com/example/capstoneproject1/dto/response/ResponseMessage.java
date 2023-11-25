package com.example.capstoneproject1.dto.response;

import org.springframework.http.HttpStatus;

public class ResponseMessage {

    private  Integer error;
    private  String message;
    private  Integer status;

    public ResponseMessage(String s, HttpStatus badRequest) {
    }

    public ResponseMessage(Integer error, String message, Integer status) {
        this.error = error;
        this.message = message;
        this.status = status;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public Integer getStatusCode() {
        return status;
    }

    public void setStatusCode(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
