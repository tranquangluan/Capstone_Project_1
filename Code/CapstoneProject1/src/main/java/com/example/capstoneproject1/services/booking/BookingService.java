package com.example.capstoneproject1.services.booking;

import com.example.capstoneproject1.models.Booking;
import com.example.capstoneproject1.models.User;

import java.util.Optional;
import org.springframework.data.domain.Page;


public interface BookingService {
    void update(Booking booking);
    Booking findById(Integer id);
    Optional<Booking> findBookingById(Integer bookingId);
    void delete(Integer id);
    Iterable<Booking> findAll();

    Boolean existsBookingWithUserAndOwner(User user, User owner);
//    Booking findBookingById(Integer id);
    void deleteBookingById(Integer id);
    Page<Booking> getAllBookings(Integer status, Integer pageNo, Integer pageSize, String sortBy, String sortDir);
}
