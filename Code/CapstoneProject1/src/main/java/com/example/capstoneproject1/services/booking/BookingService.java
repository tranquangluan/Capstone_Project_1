package com.example.capstoneproject1.services.booking;

import com.example.capstoneproject1.models.Booking;
import com.example.capstoneproject1.models.User;

import java.util.Optional;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface BookingService {
    void saveBooking(Booking booking);
//    Booking findBookingById(Integer id);
    void deleteBookingById(Integer id);
    Page<Booking> getAllBookings(Integer pageNo, Integer pageSize, String sortBy, String sortDir, BigDecimal priceFrom, BigDecimal priceTo, Integer status, Integer ownerId);

    void update(Booking booking);
    Booking findById(Integer id);
    Optional<Booking> findBookingById(Integer bookingId);
    void delete(Integer id);
    Iterable<Booking> findAll();

    Boolean existsBookingWithUserAndOwner(User user, User owner);
}
