package com.example.capstoneproject1.dto.response.sharing;

import com.example.capstoneproject1.models.Sharing;

public class SharingResponse {

    private Integer error;
    private String message;
    private Sharing sharing;
    private Integer status;


    public SharingResponse() {
    }

    public SharingResponse(Integer error, String message, Integer status) {
        this.error = error;
        this.message = message;
        this.status = status;
    }

    public SharingResponse(Integer error, String message, Sharing sharing, Integer status) {
        this.error = error;
        this.message = message;
        this.sharing = sharing;
        this.status = status;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Sharing getSharing() {
        return sharing;
    }

    public void setSharing(Sharing sharing) {
        this.sharing = sharing;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
