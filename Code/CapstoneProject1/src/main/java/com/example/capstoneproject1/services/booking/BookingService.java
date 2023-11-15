package com.example.capstoneproject1.services.booking;

import com.example.capstoneproject1.models.Booking;

public interface BookingService {
    void update(Booking booking);
    Booking findById(Integer id);
    void delete(Integer id);
    Iterable<Booking> findAll();
}
