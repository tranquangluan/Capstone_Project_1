package com.example.capstoneproject1.services.booking;

import com.example.capstoneproject1.models.Booking;

import java.util.List;

public interface BookingService {
    void update(Booking booking);
    Booking findBookingById(Integer id);
    void deleteBookingById(Integer id);
    List<Booking> getAllBookings(Integer status, Integer pageNo, Integer pageSize, String sortBy, String sortDir);
}
