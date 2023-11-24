package com.example.capstoneproject1.dto.response.booking;

import com.example.capstoneproject1.models.Booking;
import com.example.capstoneproject1.models.Space;

public class BookingResponse {
    private Integer error;
    private String message;
    private Booking booking;
    private Integer status;

    public BookingResponse() {
    }

    public BookingResponse(Integer error, String message, Integer status) {
        this.error = error;
        this.message = message;
        this.status = status;
    }

    public BookingResponse(Integer error, String message, Booking booking, Integer status) {
        this.error = error;
        this.message = message;
        this.booking = booking;
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

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
