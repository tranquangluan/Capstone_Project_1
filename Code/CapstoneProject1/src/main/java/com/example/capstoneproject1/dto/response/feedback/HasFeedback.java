package com.example.capstoneproject1.dto.response.feedback;

public class HasFeedback {

    private Integer error;

    private String message;

    private Boolean isBooking;

    private Integer status;

    public HasFeedback() {
    }

    public HasFeedback(Integer error, String message, Boolean isBooking, Integer status) {
        this.error = error;
        this.message = message;
        this.isBooking = isBooking;
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

    public Boolean getBooking() {
        return isBooking;
    }

    public void setBooking(Boolean booking) {
        isBooking = booking;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
