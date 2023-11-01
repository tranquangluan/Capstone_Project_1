package com.example.capstoneproject1.dto.response;

import org.springframework.http.HttpStatus;

public class ResponseMessage {

    private  String message;

    public ResponseMessage(String s, HttpStatus badRequest) {
    }

    public ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
