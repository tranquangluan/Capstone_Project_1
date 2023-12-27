package com.example.capstoneproject1.dto.response.booking;

import com.example.capstoneproject1.models.Booking;

import java.util.List;

public class ListBookingResponse {
    private Integer error;
    private String message;
    private Integer BookingQuantity;
    private Integer totalPages;
    private List<Booking> listBookings;
    private Integer status;

    public ListBookingResponse() {
    }

    public ListBookingResponse(Integer error, String message, Integer bookingQuantity, Integer status) {
        this.error = error;
        this.message = message;
        this.BookingQuantity = bookingQuantity;
        this.status = status;
    }

    public ListBookingResponse(Integer error, String message, Integer bookingQuantity, List<Booking> listBookings, Integer status) {
        this.error = error;
        this.message = message;
        this.BookingQuantity = bookingQuantity;
        this.listBookings = listBookings;
        this.status = status;
    }

    public ListBookingResponse(Integer error, String message, Integer bookingQuantity, Integer totalPages, List<Booking> listBookings, Integer status) {
        this.error = error;
        this.message = message;
        BookingQuantity = bookingQuantity;
        this.totalPages = totalPages;
        this.listBookings = listBookings;
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

    public Integer getBookingQuantity() {
        return BookingQuantity;
    }

    public void setBookingQuantity(Integer bookingQuantity) {
        BookingQuantity = bookingQuantity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Booking> getListBookings() {
        return listBookings;
    }

    public void setListBookings(List<Booking> listBookings) {
        this.listBookings = listBookings;
    }
}
