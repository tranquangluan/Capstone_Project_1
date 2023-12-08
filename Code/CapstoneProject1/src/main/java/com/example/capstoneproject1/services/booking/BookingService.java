package com.example.capstoneproject1.services.booking;

import com.example.capstoneproject1.models.Booking;
import com.example.capstoneproject1.models.User;

import java.util.Optional;

public interface BookingService {
    void update(Booking booking);
    Booking findById(Integer id);
    Optional<Booking> findBookingById(Integer bookingId);
    void delete(Integer id);
    Iterable<Booking> findAll();

    Boolean existsBookingWithUserAndOwner(User user, User owner);
}
